package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;
import net.lalik.shipbattles.views.ActiveBattleListViewAdapter;

public class BattleCenterActivity extends Activity {
    public final static String AUTH_TOKEN = "net.lalik.shipbattles.AUTH_TOKEN";
    private ProgressDialog findOpponentProgress;
    private Account account;
    private TextView userNick;
    private Battle[] activeBattles;
    private ListView activeBattlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_center);

        activeBattlesList = (ListView)findViewById(R.id.active_battles_list);
        userNick = (TextView)findViewById(R.id.user_nick);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences(
                "net.lalik.shipbattles.SECRETS",
                Context.MODE_PRIVATE
        );
        String authToken = sharedPreferences.getString("AUTH_TOKEN", "");

        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(authToken);
            userNick.setText(account.getNick());
        } catch (InvalidCredentialsException e) {
            finish();
        }

        activeBattles = ShipBattlesSDK
                .getInstance()
                .getActiveBattlesForAccountId(account.getId());

        ActiveBattleListViewAdapter adapter = new ActiveBattleListViewAdapter(
                this,
                activeBattles,
                account
        );

        activeBattlesList.setAdapter(adapter);
    }

    public void newBattleClicked(View view) {
        new AttackRandomOpponentTask().execute();
    }

    private class AttackRandomOpponentTask extends AsyncTask<Void, Void, Battle> {
        private ProgressDialog findOpponentProgress;

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
            return ShipBattlesSDK.getInstance().attackRandomOpponent(account);
        }

        @Override
        protected void onPostExecute(Battle battle) {
            findOpponentProgress.dismiss();
            enterBattle(battle);
        }

        private void enterBattle(Battle battle) {
            Intent intent = new Intent(BattleCenterActivity.this, BattleActivity.class);
            intent.putExtra(BattleActivity.BATTLE_ID, battle.getId());
            startActivity(intent);
        }
    }
}
