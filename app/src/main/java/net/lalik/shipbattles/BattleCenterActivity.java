package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.views.ActiveBattleListViewAdapter;

public class BattleCenterActivity extends Activity {
    public final static String AUTH_TOKEN = "net.lalik.shipbattles.AUTH_TOKEN";
    private Account account;
    private TextView userNick;
    private Battle activeBattle;
    private Battle[] finishedBattles;
    private ListView activeBattlesList;
    private ListView finishedBattlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_center);

        activeBattlesList = (ListView)findViewById(R.id.active_battles_list);
        finishedBattlesList = (ListView)findViewById(R.id.finished_battles_list);
        userNick = (TextView)findViewById(R.id.user_nick);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences(
                "net.lalik.shipbattles.SECRETS",
                Context.MODE_PRIVATE
        );
        new GetBattlesTask().execute(sharedPreferences.getString("AUTH_TOKEN", ""));
//
//        activeBattlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long battleId) {
//                Intent intent = new Intent(BattleCenterActivity.this, BattleActivity.class);
//                intent.putExtra(BattleActivity.BATTLE_ID, (int)battleId);
//                startActivity(intent);
//            }
//        });
    }

    public void newBattleClicked(View view) {
        new AttackRandomOpponentTask().execute();
    }

    private class AttackRandomOpponentTask extends AsyncTask<Void, Void, Battle> {
        private ProgressDialog findOpponentProgress;
        private boolean interrupt = false;

        @Override
        protected void onPreExecute() {
            findOpponentProgress = ProgressDialog.show(
                    BattleCenterActivity.this,
                    getText(R.string.app_name),
                    getText(R.string.we_are_finding_opponnent_for_you),
                    true
            );
        }

        @Override
        protected Battle doInBackground(Void... params) {
            Battle battle = ShipBattles.getInstance().newBattle(account);
            if (battle.getState() == 1)
                return waitForOpponent();
            return battle;
        }

        private Battle waitForOpponent() {
            while (!interrupt) {
                try {
                    Thread.sleep(5000);
                    Battle battle1 = ShipBattles.getInstance().getCurrentBattleForAccount(account);
                    if (battle1.getState() == 2) {
                        return battle1;
                    }
                } catch(InterruptedException e) {
                    interrupt = true;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Battle battle) {
            if (battle.getState() == 2) {
                findOpponentProgress.dismiss();
                enterBattle(battle);
            }
        }

        private void enterBattle(Battle battle) {
            Intent intent = new Intent(BattleCenterActivity.this, BattleActivity.class);
            intent.putExtra(BattleActivity.BATTLE_ID, battle.getId());
            startActivity(intent);
        }
    }

    private class GetBattlesTask extends AsyncTask<String, Void, Battle> {
        private ProgressDialog registerProgress;

        @Override
        protected void onPreExecute() {
            registerProgress = ProgressDialog.show(
                    BattleCenterActivity.this,
                    "ShipBattles",
                    "Fetching current battles",
                    true
            );
        }

        @Override
        protected Battle doInBackground(String... authToken) {
            account = ShipBattles.getInstance().authenticate(authToken[0]);
            activeBattle = ShipBattles.getInstance().getCurrentBattleForAccount(account);
            return activeBattle;
        }

        @Override
        protected void onPostExecute(Battle battle) {
            userNick.setText(account.getNick());

            Battle[] battles = new Battle[1];
            if (activeBattle != null)
                battles[0] = activeBattle;
            else
                battles = new Battle[0];

            ActiveBattleListViewAdapter activeBattlesAdapter = new ActiveBattleListViewAdapter(
                    BattleCenterActivity.this,
                    battles,
                    account
            );
            activeBattlesList.setAdapter(activeBattlesAdapter);

            registerProgress.dismiss();
        }
    }
}
