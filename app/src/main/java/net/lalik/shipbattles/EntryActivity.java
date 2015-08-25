package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EntryActivity extends Activity {
    private ProgressDialog registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    public void signInClicked(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void registerClicked(View view) {
        registerProgress = ProgressDialog.show(
                this,
                getText(R.string.app_name),
                getText(R.string.registering_account),
                true
        );
        new RegisterAccountTask().execute();
    }

    private void enterBattleCenter() {
        Intent intent = new Intent(this, BattleCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private class RegisterAccountTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            registerProgress.dismiss();
            enterBattleCenter();
            return null;
        }
    }
}
