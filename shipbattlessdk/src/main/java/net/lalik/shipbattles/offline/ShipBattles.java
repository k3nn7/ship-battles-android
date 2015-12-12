package net.lalik.shipbattles.offline;

import net.lalik.shipbattles.offline.entity.Battle;
import net.lalik.shipbattles.offline.service.BattleService;

public class ShipBattles {
    private static ShipBattles instance = null;
    private BattleService battleService;

    private ShipBattles() {
        battleService = new BattleService();
    }

    static public ShipBattles getInstance() {
        if (instance == null) {
            instance = new ShipBattles();
        }
        return instance;
    }

    public Battle startBattle() {
        return battleService.startBattle();
    }
}
