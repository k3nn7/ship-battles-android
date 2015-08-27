package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;
import net.lalik.shipbattles.views.ActiveBattleListViewAdapter;

import java.util.List;

public class BattleCenterActivity extends Activity {
    public final static String AUTH_TOKEN = "net.lalik.shipbattles.AUTH_TOKEN";
    private ProgressDialog findOpponentProgress;
    private Account account;
    private TextView userNick;
    private Battle[] activeBattles;
    private ListView activeBattlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_center);

        activeBattlesList = (ListView)findViewById(R.id.active_battles_list);
        userNick = (TextView)findViewById(R.id.user_nick);

        Intent intent = getIntent();
        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(intent.getStringExtra(AUTH_TOKEN));
            userNick.setText(account.getNick());
        } catch (InvalidCredentialsException e) {
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        activeBattles = ShipBattlesSDK
                .getInstance()
                .getActiveBattlesForAccountId(account.getId());

        ActiveBattleListViewAdapter adapter = new ActiveBattleListViewAdapter(
                this,
                activeBattles,
                account
        );
        activeBattlesList.setAdapter(adapter);
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

    private class BattlesArrayAdapter extends ArrayAdapter<Battle> {
        public BattlesArrayAdapter(Context context, int textViewResourceId, List<Battle> objects) {
            super(context, textViewResourceId, objects);
        }


    }
}
