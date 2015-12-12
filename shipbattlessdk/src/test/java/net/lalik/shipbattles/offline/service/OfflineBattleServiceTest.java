package net.lalik.shipbattles.offline.service;

import net.lalik.shipbattles.offline.entity.Battle;
import net.lalik.shipbattles.offline.entity.PlayerBattlefield;
import net.lalik.shipbattles.offline.entity.Ship;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OfflineBattleServiceTest {
    private BattleService battleService;

    @Before
    public void setUp() {
        battleService = new BattleService();
    }

    @Test
    public void startNewBattle() {
        Battle battle = battleService.startBattle();
        assertEquals(1, battle.getState());
    }

    @Test
    public void getStartedBattle() {
        Battle startedBattle = battleService.startBattle();
        Battle battle = battleService.getCurrentBattle();
        assertEquals(startedBattle, battle);
    }

    @Test
    public void startedBattleHasValidPlayerBattlefield() {
        Battle battle = battleService.startBattle();
        PlayerBattlefield battlefield = battle.getPlayerBattlefield();

        List<Ship> inventory = battlefield.getInventory();
        assertEquals(5, inventory.size());
    }
}
