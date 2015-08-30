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
import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.entity.ShipClass;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;
import net.lalik.shipbattles.views.BattlefieldView;
import net.lalik.shipbattles.views.Ship;
import net.lalik.shipbattles.views.ShipsInventoryListAdapter;

public class DeployFleetActivity extends Activity {
    private Account account;
    private Battle battle;
    private net.lalik.shipbattles.sdk.entity.Battlefield battlefield;
    BattlefieldView oldBattlefield;
    TextView shipsInventoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_fleet);
        oldBattlefield = (BattlefieldView)findViewById(R.id.battlefield);
        shipsInventoryText = (TextView)findViewById(R.id.ships_inventory_text);

        Intent intent = getIntent();
        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(intent.getStringExtra(BattleCenterActivity.AUTH_TOKEN));
            battle = ShipBattlesSDK
                    .getInstance()
                    .getBattleById(intent.getIntExtra(BattleActivity.BATTLE_ID, -1));
            battlefield = ShipBattlesSDK.getInstance().getBattlefieldById(
                    battle.getBattlefieldIdForAccountId(account.getId())
            );
            oldBattlefield.setBattlefield(battlefield);
        } catch (InvalidCredentialsException e) {
        } catch (EntityNotFoundException e) {
        }

        updateInventoryDisplay();
    }

    public void deployShipClicked(View view) {
        getSelectShipClassDialog().show();
    }

    public void clearBattlefieldClicked(View view) {
        oldBattlefield.clear();
    }

    public void commitBattlefieldClicked(View view) {
        new CommitBattlefieldTask().execute();
    }

    private void updateInventoryDisplay() {
        shipsInventoryText.setText("");
        for (ShipsInventory.Item item : battlefield.shipsInInventory())
            shipsInventoryText.append(String.format(
                    "%s (%d) ",
                    item.getShipClass().getName(),
                    item.getCount()
            ));
    }

    private AlertDialog.Builder getSelectShipClassDialog() {
        final ShipsInventoryListAdapter adapter = new ShipsInventoryListAdapter(
                this,
                battlefield.shipsInInventory()
        );
        return new AlertDialog.Builder(this)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getSelectCoordinatesDialog(
                                battlefield.shipsInInventory()[which].getShipClass()
                        ).show(getFragmentManager(), "CoordinatesOrientationDialogFragment");
                    }
                });
    }

    private CoordinatesOrientationDialogFragment getSelectCoordinatesDialog(final ShipClass shipClass) {
        CoordinatesOrientationDialogFragment dialog = new CoordinatesOrientationDialogFragment();
        dialog.setListener(new CoordinatesOrientationDialogFragment.CoordinatesDialogListener() {
            @Override
            public void onDeployClicked(Coordinate coordinate, Orientation orientation) {
                battlefield.deployShip(coordinate, orientation, shipClass);
                oldBattlefield.updateShots();
                updateInventoryDisplay();
            }
        });
        return dialog;
    }

    private class CommitBattlefieldTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog commitBattlefieldProgress;

        @Override
        protected void onPreExecute() {
            commitBattlefieldProgress = ProgressDialog.show(
                    DeployFleetActivity.this,
                    "ShipBattles",
                    "Wodowanie okrętów...",
                    true
            );
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ShipBattlesSDK.getInstance().deployShipsToBattlefield(battlefield);
            } catch (EntityNotFoundException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                Log.d("A", "BATTLE STATE: " + ShipBattlesSDK.getInstance().getBattleById(
                        battle.getId()
                ).getState());
            } catch (Exception e) {

            }

            commitBattlefieldProgress.dismiss();
            finish();
        }
    }
}
