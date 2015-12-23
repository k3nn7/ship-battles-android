package net.lalik.shipbattles.offline.service;

import net.lalik.shipbattles.offline.entity.Battle;
import net.lalik.shipbattles.offline.entity.Shot;
import net.lalik.shipbattles.sdk2.value.Coordinate;

public class BattleService {
    private Battle currentBattle;

    public Battle startBattle() {
        currentBattle = new Battle(1);
        return currentBattle;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public void playerShoot(Coordinate coordinate) {
        currentBattle.getOpponentBattlefield().addShot(
                new Shot(coordinate)
        );
    }
}
