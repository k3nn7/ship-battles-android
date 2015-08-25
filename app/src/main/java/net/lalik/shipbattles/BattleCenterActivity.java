package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BattleCenterActivity extends Activity {
    private ProgressDialog findOpponentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_center);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_battle_center, menu);
        return true;
    }

    public void newBattleClicked(View view) {
        findOpponentProgress = ProgressDialog.show(
                this,
                getText(R.string.app_name),
                getText(R.string.we_are_finding_opponnent_for_you),
                true
        );
        new FindOpponentTask().execute();
    }

    private void enterBattle() {
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
    }

    private class FindOpponentTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            findOpponentProgress.dismiss();
            enterBattle();
            return null;
        }
    }
}
