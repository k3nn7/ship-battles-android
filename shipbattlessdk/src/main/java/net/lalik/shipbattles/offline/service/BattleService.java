package net.lalik.shipbattles.offline.service;

import net.lalik.shipbattles.offline.entity.Battle;

public class BattleService {
    private Battle currentBattle;

    public Battle startBattle() {
        currentBattle = new Battle(1);
        return currentBattle;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }
}
