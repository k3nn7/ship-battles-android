package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;

public class BattleCenterActivity extends Activity {
    private ProgressDialog findOpponentProgress;
    private Account account;
    private TextView userNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_center);
        userNick = (TextView)findViewById(R.id.user_nick);
        Intent intent = getIntent();
        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(intent.getStringExtra(EntryActivity.AUTH_TOKEN));
            userNick.setText(account.getNick());
        } catch (InvalidCredentialsException e) {
            finish();
        }

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
