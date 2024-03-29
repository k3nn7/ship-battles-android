package net.lalik.shipbattles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends Activity {
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userName = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
    }

    public void signInClicked(View view) {
//        new SignInTask().execute(
//                userName.getText().toString(),
//                password.getText().toString()
//        );
    }

//    private class SignInTask extends AsyncTask<String, Void, Account> {
//        private ProgressDialog signInProgress;
//
//        @Override
//        protected void onPreExecute() {
//            signInProgress = ProgressDialog.show(
//                    SignInActivity.this,
//                    "ShipBattles",
//                    "Logowanie...",
//                    true
//            );
//        }
//
//        @Override
//        protected Account doInBackground(String... credentials) {
//            try {
//                return ShipBattlesSDK.getInstance().authenticate(
//                        credentials[0],
//                        credentials[1]
//                );
//            } catch (InvalidCredentialsException e) {
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Account account) {
//            signInProgress.dismiss();
//            if (null == account) {
//                showValidationErrorDialog();
//            } else {
//                enterBattleCenter(account);
//            }
//
//        }
//
//        private void showValidationErrorDialog() {
//            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//            builder.setMessage("Zła nazwa użytkownika lub hasło")
//                    .setTitle("ShipBattles");
//            builder.create().show();
//        }
//
//        private void enterBattleCenter(Account account) {
//            SharedPreferences sharedPreferences = getSharedPreferences(
//                    "net.lalik.shipbattles.SECRETS",
//                    Context.MODE_PRIVATE
//            );
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("AUTH_TOKEN", account.getAuthToken());
//            editor.commit();
//
//            Intent intent = new Intent(SignInActivity.this, BattleCenterActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
}
