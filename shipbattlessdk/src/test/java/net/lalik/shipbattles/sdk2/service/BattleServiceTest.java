package net.lalik.shipbattles.sdk2.service;

import net.lalik.shipbattles.sdk2.client.InMemoryApi;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BattleServiceTest {
    private BattleService battleService;
    private Account account;
    private Account defenderAccount;

    @Before
    public void setUp() {
        account = new Account(
                "561439f48d5e0e000c8e7f42",
                "user1444166132",
                "172bc83648184fe9b296321cd1184900"
        );
        defenderAccount = new Account(
                "5618e7dc8d5e0e000c8e7f7b",
                "user1444472796",
                "b8fb6f1d9141fcc4b3ffe4795056194d"
        );
        battleService = new BattleService(new InMemoryApi());
    }

    @Test
    public void getCurrentBattleWhenNoneExists() {
        Battle battle = battleService.getCurrentBattles(new Account("a", "b", "foo"));
        assertNull(battle);
    }

    @Test
    public void getCurrentBattleWhenOneExists() {
        Battle battle = battleService.getCurrentBattles(account);

        assertNotNull(battle);
        assertEquals("560affee3492eb00087355fb", battle.getId());
        assertEquals("560affde3492eb00087355f9", battle.getAttackerId());
        assertEquals("561439f48d5e0e000c8e7f42", battle.getDefenderId());
        assertEquals(2, battle.getState());

        testMyBattlefield(battle.getMyBattlefield());

        assertEquals("56143bec8d5e0e000c8e7f46", battle.getOpponentBattlefield().getId());
        assertEquals("560affde3492eb00087355f9", battle.getOpponentBattlefield().getAccountId());
        assertEquals(false, battle.getOpponentBattlefield().isReadyForBattle());
    }

    private void testMyBattlefield(MyBattlefield battlefield) {
        assertEquals("56143bec8d5e0e000c8e7f47", battlefield.getId());
        assertEquals("561439f48d5e0e000c8e7f42", battlefield.getAccountId());
        assertEquals(1, battlefield.getInventory().get("is:0").intValue());
        assertEquals(1, battlefield.getInventory().get("is:1").intValue());
        assertEquals(false, battlefield.isReadyForBattle());

        ShipClass[] shipClasses = battlefield.getAvailableShipClasses();
        assertEquals(2, shipClasses.length);
        assertEquals("keel", shipClasses[0].getName());
        assertEquals("destroyer", shipClasses[1].getName());
        assertEquals(1, battlefield.shipsCountInInventory(shipClasses[0]));
        assertEquals(1, battlefield.shipsCountInInventory(shipClasses[1]));
    }

    @Test
    public void startNewBattle() {
        Battle battle = battleService.newBattle(account);
        assertNotNull(battle);
        assertEquals("5618e5d78d5e0e000c8e7f76", battle.getId());
        assertEquals("5618e5c28d5e0e000c8e7f74", battle.getAttackerId());
        assertEquals(1, battle.getState());
        assertNull(battle.getDefenderId());
    }

    @Test
    public void joinBattle() {
        Battle battle = battleService.newBattle(defenderAccount);
        assertNotNull(battle);
        assertEquals("5618e7f38d5e0e000c8e7f7d", battle.getId());
        assertEquals("5618e7dc8d5e0e000c8e7f7b", battle.getAttackerId());
        assertEquals("5618e7f88d5e0e000c8e7f7e", battle.getDefenderId());
        assertEquals(2, battle.getState());
    }

    @Test
    public void getShipClasses() {
        HashMap<String, ShipClass> shipClasses = battleService.getShipClasses();

        assertEquals(2, shipClasses.size());

        assertEquals(1, shipClasses.get("is:0").getSize());
        assertEquals("is:0", shipClasses.get("is:0").getId());
        assertEquals("keel", shipClasses.get("is:0").getName());

        assertEquals(2, shipClasses.get("is:1").getSize());
        assertEquals("is:1", shipClasses.get("is:1").getId());
        assertEquals("destroyer", shipClasses.get("is:1").getName());
    }

    @Test
    public void readyForBattle() {
        battleService.readyForBattle(account);
    }
}
