package net.lalik.shipbattles;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.lalik.shipbattles.fragments.CoordinatesDialogFragment;
import net.lalik.shipbattles.sdk.values.Coordinate;

public class AttackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
    }

    public void attackClicked(View view) {
        getCoordinatesDialog().show(getFragmentManager(), "CoordinatesDialogFragment");
    }

    private CoordinatesDialogFragment getCoordinatesDialog() {
        CoordinatesDialogFragment dialog = new CoordinatesDialogFragment();
        dialog.setListener(new CoordinatesDialogFragment.CoordinatesDialogListener() {
            @Override
            public void onDeployClicked(Coordinate coordinate) {

            }
        });
        return dialog;
    }
}
