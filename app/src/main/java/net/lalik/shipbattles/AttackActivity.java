package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.Battlefield;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.views.BattlefieldView;
import net.lalik.shipbattles.views.OpponentBattlefieldView;

public class AttackActivity extends Activity {

    private Account account;
    private Battle battle;
    private Battlefield battlefield;
    private OpponentBattlefieldView battlefieldView;
    private BattlefieldView myBattlefieldView;
    private MyBattlefield myBattlefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        battlefieldView = (OpponentBattlefieldView)findViewById(R.id.battlefield);
        myBattlefieldView = (BattlefieldView)findViewById(R.id.battlefield2);
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
        @Override
        protected Battle doInBackground(Coordinate... params) {
            ShipBattles.getInstance().fire(account, params[0]);
            return ShipBattles.getInstance().getCurrentBattleForAccount(account);
        }

        @Override
        protected void onPostExecute(Battle b) {
            battle = b;
            battlefieldView.setBattlefield(b.getOpponentBattlefield());
            myBattlefieldView.setBattlefield(b.getMyBattlefield());
            battlefieldView.updateShots();
            myBattlefieldView.updateShots();
        }
    }
}
