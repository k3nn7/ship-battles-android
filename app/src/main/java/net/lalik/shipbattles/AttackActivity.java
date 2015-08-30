package net.lalik.shipbattles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.lalik.shipbattles.fragments.CoordinatesDialogFragment;
import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;
import net.lalik.shipbattles.sdk.values.AttackResult;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.views.BattlefieldView;

public class AttackActivity extends Activity {

    private Account account;
    private Battle battle;
    private Battlefield battlefield;
    private BattlefieldView battlefieldView;
    private BattlefieldView myBattlefieldView;
    private Battlefield myBattlefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        battlefieldView = (BattlefieldView)findViewById(R.id.battlefield);
        myBattlefieldView = (BattlefieldView)findViewById(R.id.battlefield2);

        Intent intent = getIntent();
        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(intent.getStringExtra(BattleCenterActivity.AUTH_TOKEN));
            battle = ShipBattlesSDK
                    .getInstance()
                    .getBattleById(intent.getIntExtra(BattleActivity.BATTLE_ID, -1));
            battlefield = ShipBattlesSDK.getInstance().getBattlefieldById(
                    battle.getBattlefieldIdForAccountId(
                            battle.getSecondAccountId(account.getId()).getId()
                    )
            );
            myBattlefield = ShipBattlesSDK.getInstance().getBattlefieldById(
                    battle.getBattlefieldIdForAccountId(account.getId())
            );
            battlefieldView.setBattlefield(battlefield);
            myBattlefieldView.setBattlefield(myBattlefield);
        } catch (InvalidCredentialsException e) {
        } catch (EntityNotFoundException e) {
        }
    }

    public void attackClicked(View view) {
        getCoordinatesDialog().show(getFragmentManager(), "CoordinatesDialogFragment");
    }

    private CoordinatesDialogFragment getCoordinatesDialog() {
        CoordinatesDialogFragment dialog = new CoordinatesDialogFragment();
        dialog.setListener(new CoordinatesDialogFragment.CoordinatesDialogListener() {
            @Override
            public void onDeployClicked(Coordinate coordinate) {
                AttackResult result = ShipBattlesSDK.getInstance().attackBattlefield(battlefield, coordinate);
                battlefieldView.updateShots();
                myBattlefield.attack(new Coordinate(1, 1));
                myBattlefieldView.updateShots();
            }
        });
        return dialog;
    }
}
