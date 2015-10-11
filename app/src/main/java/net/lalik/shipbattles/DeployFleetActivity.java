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
import net.lalik.shipbattles.sdk2.entity.Battlefield;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.views.BattlefieldView;
import net.lalik.shipbattles.views.Ship;
import net.lalik.shipbattles.views.ShipsInventoryListAdapter;

import java.util.Map;

public class DeployFleetActivity extends Activity {
    private Account account;
    private Battle battle;
    private MyBattlefield battlefield;
    private BattlefieldView oldBattlefield;
    private TextView shipsInventoryText;
    private Map<String, ShipClass> shipClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_fleet);
        oldBattlefield = (BattlefieldView)findViewById(R.id.battlefield);
        shipsInventoryText = (TextView)findViewById(R.id.ships_inventory_text);

        Intent intent = getIntent();
        new GetBattlesTask().execute(intent.getStringExtra(BattleCenterActivity.AUTH_TOKEN));
    }

    public void deployShipClicked(View view) {
//        getSelectShipClassDialog().show();
    }

    public void clearBattlefieldClicked(View view) {
        oldBattlefield.clear();
    }

    public void commitBattlefieldClicked(View view) {
//        new CommitBattlefieldTask().execute();
    }

    private void updateInventoryDisplay() {
        shipsInventoryText.setText("");
        for (Map.Entry<String, Integer> entry : battlefield.getInventory().entrySet()) {
            String shipClassId = entry.getKey();
            Integer shipsCount = entry.getValue();
            shipsInventoryText.append(String.format(
                    "%s (%d)",
                    shipClasses.get(shipClassId).getName(),
                    shipsCount
            ));
        }
    }

//    private AlertDialog.Builder getSelectShipClassDialog() {
//        final ShipsInventoryListAdapter adapter = new ShipsInventoryListAdapter(
//                this,
//                battlefield.shipsInInventory()
//        );
//        return new AlertDialog.Builder(this)
//                .setAdapter(adapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        getSelectCoordinatesDialog(
//                                battlefield.shipsInInventory()[which].getShipClass()
//                        ).show(getFragmentManager(), "CoordinatesOrientationDialogFragment");
//                    }
//                });
//    }
//
//    private CoordinatesOrientationDialogFragment getSelectCoordinatesDialog(final ShipClass shipClass) {
//        CoordinatesOrientationDialogFragment dialog = new CoordinatesOrientationDialogFragment();
//        dialog.setListener(new CoordinatesOrientationDialogFragment.CoordinatesDialogListener() {
//            @Override
//            public void onDeployClicked(Coordinate coordinate, Orientation orientation) {
//                battlefield.deployShip(coordinate, orientation, shipClass);
//                oldBattlefield.updateShots();
//                updateInventoryDisplay();
//            }
//        });
//        return dialog;
//    }

//    private class CommitBattlefieldTask extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog commitBattlefieldProgress;
//
//        @Override
//        protected void onPreExecute() {
//            commitBattlefieldProgress = ProgressDialog.show(
//                    DeployFleetActivity.this,
//                    "ShipBattles",
//                    "Wodowanie okrętów...",
//                    true
//            );
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                ShipBattlesSDK.getInstance().deployShipsToBattlefield(battlefield);
//            } catch (EntityNotFoundException e) {
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            try {
//                Log.d("A", "BATTLE STATE: " + ShipBattlesSDK.getInstance().getBattleById(
//                        battle.getId()
//                ).getState());
//            } catch (Exception e) {
//
//            }
//
//            commitBattlefieldProgress.dismiss();
//            finish();
//        }
//    }

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
            shipClasses = ShipBattles.getInstance().getShipClasses();
            return ShipBattles.getInstance().getCurrentBattleForAccount(account);
        }

        @Override
        protected void onPostExecute(net.lalik.shipbattles.sdk2.entity.Battle b) {
            battle = b;

            battlefield = battle.getMyBattlefield();

//            oldBattlefield.setBattlefield(battlefield);

            updateInventoryDisplay();

            registerProgress.dismiss();
        }
    }
}
