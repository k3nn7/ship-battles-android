package net.lalik.shipbattles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.lalik.shipbattles.sdk2.ShipBattles;
import net.lalik.shipbattles.sdk2.entity.Account;

public class EntryActivity extends Activity {
    private ProgressDialog registerProgress;
    private TextView entryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        entryText = (TextView)findViewById(R.id.entry_text);
        Typeface font = Typeface.createFromAsset(getAssets(), "black_chancery.ttf");
        entryText.setTypeface(font);
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

    private void enterBattleCenter(Account account) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "net.lalik.shipbattles.SECRETS",
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AUTH_TOKEN", account.getSessionToken());
        editor.commit();
        Intent intent = new Intent(this, BattleCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private class RegisterAccountTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Account account = ShipBattles.getInstance().createRandomAccount();
            registerProgress.dismiss();
            enterBattleCenter(account);
            return null;
        }
    }
}
