package net.lalik.shipbattles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.Battlefield;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.Ship;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.views.MyBattlefieldView;
import net.lalik.shipbattles.views.OpponentBattlefieldView;

public class AttackActivity extends Activity {

    private Account account;
    private Battle battle;
    private Battlefield battlefield;
    private OpponentBattlefieldView battlefieldView;
    private MyBattlefieldView myBattlefieldView;
    private MyBattlefield myBattlefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        battlefieldView = (OpponentBattlefieldView)findViewById(R.id.opponentBattlefield);
        myBattlefieldView = (MyBattlefieldView)findViewById(R.id.myBattlefield);
        battlefieldView.setCoordinateSelectedListener(new AttackListener());

        SharedPreferences sharedPreferences = getSharedPreferences(
                "net.lalik.shipbattles.SECRETS",
                Context.MODE_PRIVATE
        );
        String authToken = sharedPreferences.getString("AUTH_TOKEN", "");
        new GetBattleTask().execute(authToken);
    }

    private void attack(Coordinate coordinate) {
        try {
            new FireTask().execute(coordinate);
        } catch (Exception e) {
        }
    }

    private class AttackListener implements OpponentBattlefieldView.CoordinateSelectedListener {
        @Override
        public void onCoordinateSelected(Coordinate coordinate) {
            attack(coordinate);
        }
    }

    private class GetBattleTask extends AsyncTask<String, Void, Battle> {
        private ProgressDialog registerProgress;

        @Override
        protected void onPreExecute() {
            registerProgress = ProgressDialog.show(
                    AttackActivity.this,
                    "ShipBattles",
                    "Loading battle",
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
            myBattlefield = battle.getMyBattlefield();
            battlefield = battle.getOpponentBattlefield();
            battlefieldView.setBattlefield(battlefield);
            myBattlefieldView.setBattlefield(myBattlefield);
            registerProgress.dismiss();
        }
    }

    private class FireTask extends AsyncTask<Coordinate, Void, Battle> {
        int result;
        @Override
        protected Battle doInBackground(Coordinate... params) {
            result = ShipBattles.getInstance().fire(account, params[0]);
            return ShipBattles.getInstance().getCurrentBattleForAccount(account);
        }

        @Override
        protected void onPostExecute(Battle b) {
            battle = b;
            battlefieldView.setBattlefield(b.getOpponentBattlefield());
            myBattlefieldView.setBattlefield(b.getMyBattlefield());
            battlefieldView.updateShots();
            myBattlefieldView.updateShots();
            
            AlertDialog.Builder alert = new AlertDialog.Builder(AttackActivity.this);
            switch (result) {
                case 1:
                    alert.setMessage("Pudło!");
                    break;
                case 2:
                    alert.setMessage("Trafiony!");
                    break;
                case 3:
                    alert.setMessage("Zatopiony");
                    break;
            }
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new WaitForOpponentTask().execute();
                }
            });
            alert.create().show();
        }
    }

    private class WaitForOpponentTask extends AsyncTask<Void, Void, Battle> {
        private ProgressDialog waitForOpponentProgress;
        private boolean interrupt = false;

        @Override
        protected void onPreExecute() {
            waitForOpponentProgress = ProgressDialog.show(
                    AttackActivity.this,
                    "ShipBattles",
                    "Waiting for opponent attack",
                    true
            );
        }

        @Override
        protected Battle doInBackground(Void... params) {
            return waitForOpponent();
        }

        private Battle waitForOpponent() {
            while (!interrupt) {
                try {
                    Thread.sleep(5000);
                    Battle battle1 = ShipBattles.getInstance().getCurrentBattleForAccount(account);
                    if (battle1.getTurnAccountId().equals(account.getId())) {
                        return battle1;
                    }
                } catch(InterruptedException e) {
                    interrupt = true;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Battle b) {
            waitForOpponentProgress.dismiss();
            battle = b;
            battlefieldView.setBattlefield(b.getOpponentBattlefield());
            myBattlefieldView.setBattlefield(b.getMyBattlefield());
            battlefieldView.updateShots();
            myBattlefieldView.updateShots();
        }
    }
}
