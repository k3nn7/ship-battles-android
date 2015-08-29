package net.lalik.shipbattles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        Intent intent = getIntent();
        try {
            account = ShipBattlesSDK
                    .getInstance()
                    .authenticate(intent.getStringExtra(BattleCenterActivity.AUTH_TOKEN));
            battle = ShipBattlesSDK
                    .getInstance()
                    .getBattleById(intent.getIntExtra(BATTLE_ID, -1));
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
