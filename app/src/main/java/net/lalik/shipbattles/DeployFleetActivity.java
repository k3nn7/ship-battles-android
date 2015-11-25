package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;
import net.lalik.shipbattles.views.DeployBattlefieldView;

import java.util.Map;

public class DeployFleetActivity extends Activity {
    private Account account;
    private Battle battle;
    private MyBattlefield battlefield;
    private DeployBattlefieldView myBattlefieldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_fleet);
        myBattlefieldView = (DeployBattlefieldView) findViewById(R.id.battlefield);

        Intent intent = getIntent();
        String sessionToken = "172bc83648184fe9b296321cd1184900";
//        sessionToken = intent.getStringExtra(BattleCenterActivity.AUTH_TOKEN);
        new GetBattlesTask().execute(sessionToken);
    }

    private class GetBattlesTask extends AsyncTask<String, Void, net.lalik.shipbattles.sdk2.entity.Battle> {
        private ProgressDialog registerProgress;
        private Map<String, ShipClass> shipClasses;

        @Override
        protected void onPreExecute() {
            registerProgress = ProgressDialog.show(
                    DeployFleetActivity.this,
                    "ShipBattles",
                    "Fetching current battles",
                    true
            );
        }

        @Override
        protected net.lalik.shipbattles.sdk2.entity.Battle doInBackground(String... authToken) {
            account = ShipBattles.getInstance().authenticate(authToken[0]);
            shipClasses = ShipBattles.getInstance().getShipClasses();
            return ShipBattles.getInstance().getCurrentBattleForAccount(account);
        }

        @Override
        protected void onPostExecute(net.lalik.shipbattles.sdk2.entity.Battle b) {
            battle = b;
            battlefield = battle.getMyBattlefield();
            myBattlefieldView.setBattlefield(battlefield);
            registerProgress.dismiss();
            myBattlefieldView.invalidate();
        }
    }

    private class DeployShipTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            Account account = (Account) params[0];
            ShipClass shipClass = (ShipClass) params[1];
            Coordinate coordinate = (Coordinate) params[2];
            Orientation orientation = (Orientation) params[3];

            battlefield = ShipBattles.getInstance().deployShip(
                    account,
                    shipClass,
                    coordinate,
                    orientation);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            myBattlefieldView.setBattlefield(battlefield);
        }
    }

    private class ReadyForBattleTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ShipBattles.getInstance().readyForBattle(account);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
        }
    }
}
