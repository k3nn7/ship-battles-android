package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;

public class BattleActivity extends Activity {
    public static final String BATTLE_ID = "net.lalik.shipbattles.BATTLE_ID";
    private Integer battleId = null;
    private Account account;
    private Battle battle;
    private TextView attackerNick;
    private TextView defenderNick;
    private TextView battleState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        attackerNick = (TextView)findViewById(R.id.attacker_nick);
        defenderNick = (TextView)findViewById(R.id.defender_nick);
        battleState = (TextView)findViewById(R.id.battle_state);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(
                "net.lalik.shipbattles.SECRETS",
                Context.MODE_PRIVATE
        );
        String authToken = sharedPreferences.getString("AUTH_TOKEN", "");
        Intent intent = getIntent();
        if (intent.getIntExtra(BATTLE_ID, -1) != -1) {
            battleId = intent.getIntExtra(BATTLE_ID, -1);
        }

        new GetBattlesTask().execute(authToken);
    }

    public void deployFleetClicked(View view) {
        Intent intent = new Intent(this, DeployFleetActivity.class);
        intent.putExtra(BattleActivity.BATTLE_ID, battle.getId());
        intent.putExtra(BattleCenterActivity.AUTH_TOKEN, account.getSessionToken());
        startActivity(intent);
    }

    public void attackClicked(View view) {
        new BeginFireExchangeTask().execute();
    }

    private String formatBattleState() {
        switch (battle.getState()) {
            case 1:
                return "oczekiwanie na przeciwnika";
            case 2:
                return "wodowanie statków";
            case 3:
                return "w trakcie bitwy";
        }
        return "invalid ";
    }

    private class GetBattlesTask extends AsyncTask<String, Void, Battle> {
        private ProgressDialog registerProgress;

        @Override
        protected void onPreExecute() {
            registerProgress = ProgressDialog.show(
                    BattleActivity.this,
                    "ShipBattles",
                    "Fetching current battles",
                    true
            );
        }

        @Override
        protected Battle doInBackground(String... authToken) {
            account = ShipBattles.getInstance().authenticate(authToken[0]);
            return ShipBattles.getInstance().getCurrentBattleForAccount(account);
        }

        @Override
        protected void onPostExecute(Battle b) {
            battle = b;

            attackerNick.setText(battle.getAttackerId());
            defenderNick.setText(battle.getDefenderId());
            battleState.setText(formatBattleState());

            registerProgress.dismiss();
        }
    }

    private class BeginFireExchangeTask extends AsyncTask<Void, Void, Battle> {
        private ProgressDialog waitForOpponentProgress;
        private boolean interrupt = false;

        @Override
        protected void onPreExecute() {
            waitForOpponentProgress = ProgressDialog.show(
                    BattleActivity.this,
                    "ShipBattles",
                    "Waiting for opponent to be ready",
                    true
            );
        }

        @Override
        protected Battle doInBackground(Void... params) {
            Battle battle = ShipBattles.getInstance().getCurrentBattleForAccount(account);
            if (battle.getState() != 3)
                return waitForOpponent();
            return battle;
        }

        private Battle waitForOpponent() {
            while (!interrupt) {
                try {
                    Thread.sleep(5000);
                    Battle battle1 = ShipBattles.getInstance().getCurrentBattleForAccount(account);
                    if (battle1.getState() == 3) {
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
            if (battle.getState() == 3) {
                waitForOpponentProgress.dismiss();
                enterFireExchange(battle);
            }
        }

        private void enterFireExchange(Battle battle) {
            Intent intent = new Intent(BattleActivity.this, AttackActivity.class);
            intent.putExtra(BATTLE_ID, battle.getId());
            intent.putExtra(BattleCenterActivity.AUTH_TOKEN, account.getSessionToken());
            startActivity(intent);
        }
    }
}
