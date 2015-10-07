package net.lalik.shipbattles.sdk2.service;

import net.lalik.shipbattles.sdk2.client.InMemoryApi;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BattleServiceTest {
    private BattleService battleService;
    private Account account;

    @Before
    public void setUp() {
        account = new Account(
                "561439f48d5e0e000c8e7f42",
                "user1444166132",
                "172bc83648184fe9b296321cd1184900"
        );
        battleService = new BattleService(new InMemoryApi());
    }

    @Test
    public void getCurrentBattleWhenOneExists() {
        Battle battle = battleService.getCurrentBattles(account);

        assertNotNull(battle);
        assertEquals("560affee3492eb00087355fb", battle.getId());
        assertEquals("560affde3492eb00087355f9", battle.getAttackerId());
        assertEquals("561439f48d5e0e000c8e7f42", battle.getDefenderId());
        assertEquals(2, battle.getState());

        assertEquals("56143bec8d5e0e000c8e7f47", battle.getMyBattlefield().getId());
        assertEquals("561439f48d5e0e000c8e7f42", battle.getMyBattlefield().getAccountId());
        assertEquals(false, battle.getMyBattlefield().isReadyForBattle());

        assertEquals("56143bec8d5e0e000c8e7f46", battle.getOpponentBattlefield().getId());
        assertEquals("560affde3492eb00087355f9", battle.getOpponentBattlefield().getAccountId());
        assertEquals(false, battle.getOpponentBattlefield().isReadyForBattle());
    }
}
