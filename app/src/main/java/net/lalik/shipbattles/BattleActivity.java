package net.lalik.shipbattles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.lalik.shipbattles.sdk.ShipBattlesSDK;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;

import org.w3c.dom.Text;

public class BattleActivity extends Activity {
    public static final String BATTLE_ID = "net.lalik.shipbattles.BATTLE_ID";
    private Account account;
    private Battle battle;
    private TextView attackerNick;
    private TextView defenderNick;
    private TextView battleState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        attackerNick = (TextView)findViewById(R.id.attacker_nick);
        defenderNick = (TextView)findViewById(R.id.defender_nick);
        battleState = (TextView)findViewById(R.id.battle_state);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("A", "ON START INVOKED");
        SharedPreferences sharedPreferences = getSharedPreferences(
                "net.lalik.shipbattles.SECRETS",
                Context.MODE_PRIVATE
        );
        String authToken = sharedPreferences.getString("AUTH_TOKEN", "");
        Intent intent = getIntent();
        int battleId;
        if (intent.getIntExtra(BATTLE_ID, -1) != -1) {
            battleId = intent.getIntExtra(BATTLE_ID, -1);
        } else {
            battleId = battle.getId();
        }

        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(authToken);
            battle = ShipBattlesSDK
                    .getInstance()
                    .getBattleById(battleId);
        } catch (InvalidCredentialsException e) {
        } catch (EntityNotFoundException e) {
        }

        attackerNick.setText(battle.getLeftAccount().getNick());
        defenderNick.setText(battle.getRightAccount().getNick());
        battleState.setText(formatBattleState());
    }

    public void deployFleetClicked(View view) {
        Intent intent = new Intent(this, DeployFleetActivity.class);
        intent.putExtra(BattleActivity.BATTLE_ID, battle.getId());
        intent.putExtra(BattleCenterActivity.AUTH_TOKEN, account.getAuthToken());
        startActivity(intent);
    }

    public void attackClicked(View view) {
        Intent intent = new Intent(this, AttackActivity.class);
        intent.putExtra(BATTLE_ID, battle.getId());
        startActivity(intent);
    }

    private String formatBattleState() {
        switch (battle.getState()) {
            case DEPLOY:
                return "wodowanie statków";
            case FIRE_EXCHANGE:
                return "w trakcie bitwy";
        }
        return "invalid ";
    }
}
