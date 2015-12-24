package net.lalik.shipbattles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import net.lalik.shipbattles.offline.ShipBattles;
import net.lalik.shipbattles.offline.entity.Battle;
import net.lalik.shipbattles.offline.entity.PlayerBattlefield;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.views.OfflineMyBattlefieldView;
import net.lalik.shipbattles.views.OfflineOpponentBattlefieldView;

public class FastAttackActivity extends Activity {
    private OfflineMyBattlefieldView myBattlefieldView;
    private OfflineOpponentBattlefieldView opponentBattlefieldView;
    private TextView currentTurn;
    private TextView playerShips;
    private TextView opponentShips;

    private Battle battle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_attack);

        currentTurn = (TextView)findViewById(R.id.current_turn);
        playerShips = (TextView)findViewById(R.id.player_ships);
        opponentShips = (TextView)findViewById(R.id.opponent_ships);

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

            int liveShips = battle.getOpponentBattlefield().getInventory().size() - battle.getOpponentBattlefield().getDestroyedShipsCount();
            opponentShips.setText(String.format(
                    "Ships: %d/%d",
                    liveShips,
                    battle.getOpponentBattlefield().getInventory().size()
            ));

            opponentBattlefieldView.invalidate();
            new OpponentAttackTask().execute();
        }
    }

    private class OpponentAttackTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            currentTurn.setText("Current turn: opponent");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            ShipBattles.getInstance().opponentShot();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            currentTurn.setText("Current turn: player");
            myBattlefieldView.invalidate();

            int liveShips = battle.getPlayerBattlefield().getInventory().size() - battle.getPlayerBattlefield().getDestroyedShipsCount();
            playerShips.setText(String.format(
                    "Ships: %d/%d",
                    liveShips,
                    battle.getPlayerBattlefield().getInventory().size()
            ));
        }
    }
}
