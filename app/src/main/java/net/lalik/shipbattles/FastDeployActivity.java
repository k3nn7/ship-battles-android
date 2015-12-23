package net.lalik.shipbattles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.lalik.shipbattles.offline.ShipBattles;
import net.lalik.shipbattles.offline.entity.Battle;
import net.lalik.shipbattles.views.OfflineDeployBattlefieldView;

public class FastDeployActivity extends Activity {
    private OfflineDeployBattlefieldView deployBattlefieldView;
    private Battle battle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_deploy);
        deployBattlefieldView = (OfflineDeployBattlefieldView) findViewById(R.id.deploy_battlefield_view);

        battle = ShipBattles.getInstance().startBattle();
        deployBattlefieldView.setBattlefield(battle.getPlayerBattlefield());
        deployBattlefieldView.invalidate();
    }

    public void startBattleClicked(View view) {
        deployBattlefieldView.updateOffset();
        Intent intent = new Intent(this, FastAttackActivity.class);
        startActivity(intent);
    }
}
