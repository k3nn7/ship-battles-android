package net.lalik.shipbattles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.lalik.shipbattles.fragments.CoordinatesDialogFragment;
import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;
import net.lalik.shipbattles.sdk.values.ShipsInventory;
import net.lalik.shipbattles.views.Battlefield;
import net.lalik.shipbattles.views.Coordinate;
import net.lalik.shipbattles.views.Ship;
import net.lalik.shipbattles.views.ShipInventory;

import java.util.HashMap;
import java.util.Map;

public class DeployFleetActivity extends Activity {
    private Account account;
    private Battle battle;
    private net.lalik.shipbattles.sdk.entity.Battlefield battlefield;
    Battlefield oldBattlefield;
    ShipInventory shipsInventory;
    TextView shipsInventoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_fleet);
        oldBattlefield = (Battlefield)findViewById(R.id.battlefield);
        shipsInventoryText = (TextView)findViewById(R.id.ships_inventory_text);

        shipsInventory = new ShipInventory();
        shipsInventory.addShips(Ship.Type.KEEL, 4);
        shipsInventory.addShips(Ship.Type.FRIGATE, 3);

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
        } catch (InvalidCredentialsException e) {
        } catch (EntityNotFoundException e) {
        }

        updateInventoryDisplay();
    }

    public void deployShipClicked(View view) {
        String [] items = new String[shipsInventory.availableShips().size()];
        final HashMap<Integer, Ship.Type> indexToType = new HashMap<>();
        int i = 0;
        for (Map.Entry<Ship.Type, Integer> item : shipsInventory.availableShips()) {
            items[i] = String.format(
                    "%s (%d) ",
                    item.getKey().getName(),
                    item.getValue()
            );
            indexToType.put(i, item.getKey());
            i++;
        }

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getCoordinates(indexToType.get(which));

                    }
                }).show();
    }

    public void clearBattlefieldClicked(View view) {
        oldBattlefield.clear();
    }

    public void deployFinishedClicked(View view) {
        finish();
    }

    public void getCoordinates(Ship.Type shipType) {
        CoordinatesDialogFragment dialog = new CoordinatesDialogFragment();
        dialog.setListener(new DeployListener(shipType));
        dialog.show(getFragmentManager(), "CoordinatesDialogFragment");
    }

    class DeployListener implements CoordinatesDialogFragment.CoordinatesDialogListener {
        private Ship.Type shipType;

        public DeployListener(Ship.Type shipType) {
            this.shipType = shipType;
        }

        @Override
        public void onDeployClicked(Coordinate coordinate, Ship.Orientation orientation) {
            oldBattlefield.deployShip(new Ship(
                    coordinate,
                    orientation,
                    shipType.getSize()
            ));
            shipsInventory.pickShip(shipType);
            updateInventoryDisplay();
        }
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
}
