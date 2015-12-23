package net.lalik.shipbattles;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import net.lalik.shipbattles.offline.ShipBattles;
import net.lalik.shipbattles.offline.entity.Battle;
import net.lalik.shipbattles.offline.entity.PlayerBattlefield;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.views.OfflineMyBattlefieldView;
import net.lalik.shipbattles.views.OfflineOpponentBattlefieldView;

public class FastAttackActivity extends Activity {
    private OfflineMyBattlefieldView myBattlefieldView;
    private OfflineOpponentBattlefieldView opponentBattlefieldView;

    private Battle battle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_attack);

        myBattlefieldView = (OfflineMyBattlefieldView)findViewById(R.id.my_battlefield_view);
        opponentBattlefieldView = (OfflineOpponentBattlefieldView)findViewById(R.id.opponent_battlefield_view);

        battle = ShipBattles.getInstance().getCurrentBattle();

        myBattlefieldView.setBattlefield(battle.getPlayerBattlefield());
        myBattlefieldView.invalidate();

        opponentBattlefieldView.setBattlefield(battle.getOpponentBattlefield());
        opponentBattlefieldView.setCoordinateSelectedListener(new AttackListener());
        opponentBattlefieldView.invalidate();
    }

    private class AttackListener implements OfflineOpponentBattlefieldView.CoordinateSelectedListener {
        @Override
        public void onCoordinateSelected(Coordinate coordinate) {
            ShipBattles.getInstance().playerShoot(coordinate);
            opponentBattlefieldView.invalidate();
        }
    }
}
