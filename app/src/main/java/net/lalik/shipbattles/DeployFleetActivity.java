package net.lalik.shipbattles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.lalik.shipbattles.fragments.CoordinatesOrientationDialogFragment;
import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;
import net.lalik.shipbattles.views.BattlefieldView;
import net.lalik.shipbattles.views.ShipsInventoryListAdapter;

import java.util.Map;
import java.util.Stack;

public class DeployFleetActivity extends Activity {
    private Account account;
    private Battle battle;
    private MyBattlefield battlefield;
    private BattlefieldView battlefieldView;
    private TextView shipsInventoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_fleet);
        battlefieldView = (BattlefieldView)findViewById(R.id.battlefield);
        shipsInventoryText = (TextView)findViewById(R.id.ships_inventory_text);

        Intent intent = getIntent();
        new GetBattlesTask().execute(intent.getStringExtra(BattleCenterActivity.AUTH_TOKEN));
    }

    public void deployShipClicked(View view) {
        getSelectShipClassDialog().show();
    }

    public void clearBattlefieldClicked(View view) {
        battlefieldView.clear();
    }

    public void commitBattlefieldClicked(View view) {
        new CommitBattlefieldTask().execute();
    }

    private void updateInventoryDisplay() {
        shipsInventoryText.setText("");
        ShipClass[] availableShipClasses = battlefield.getAvailableShipClasses();
        for (ShipClass shipClass : availableShipClasses) {
            shipsInventoryText.append(String.format(
                    "%s (%d)",
                    shipClass.getName(),
                    battlefield.shipsCountInInventory(shipClass)
            ));
        }
    }

    private AlertDialog.Builder getSelectShipClassDialog() {
        final ShipsInventoryListAdapter adapter = new ShipsInventoryListAdapter(
                this,
                battlefield.getAvailableShipClasses()
        );
        return new AlertDialog.Builder(this)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getSelectCoordinatesDialog(
                                adapter.getItem(which)
                        ).show(getFragmentManager(), "CoordinatesOrientationDialogFragment");
                    }
                });
    }

    private CoordinatesOrientationDialogFragment getSelectCoordinatesDialog(final ShipClass shipClass) {
        CoordinatesOrientationDialogFragment dialog = new CoordinatesOrientationDialogFragment();
        dialog.setListener(new CoordinatesOrientationDialogFragment.CoordinatesDialogListener() {
            @Override
            public void onDeployClicked(Coordinate coordinate, Orientation orientation) {
                new DeployShipTask().execute(
                        account,
                        shipClass,
                        coordinate
                );
            }
        });
        return dialog;
    }

    private class GetBattlesTask extends AsyncTask<String, Void, net.lalik.shipbattles.sdk2.entity.Battle> {
        private ProgressDialog registerProgress;

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
            return ShipBattles.getInstance().getCurrentBattleForAccount(account);
        }

        @Override
        protected void onPostExecute(net.lalik.shipbattles.sdk2.entity.Battle b) {
            battle = b;

            battlefield = battle.getMyBattlefield();

            battlefieldView.setBattlefield(battlefield);

            updateInventoryDisplay();

            registerProgress.dismiss();
        }
    }

    private class DeployShipTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            Account account = (Account)params[0];
            ShipClass shipClass = (ShipClass)params[1];
            Coordinate coordinate = (Coordinate)params[2];

            battlefield = ShipBattles.getInstance().deployShip(
                    account,
                    shipClass,
                    coordinate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            battlefieldView.setBattlefield(battlefield);
            battlefieldView.updateShots();
            updateInventoryDisplay();
        }
    }

    private class CommitBattlefieldTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog commitBattlefieldProgress;


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
