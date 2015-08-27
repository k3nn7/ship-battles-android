package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattleRepository;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.BattleRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BattleServiceTest {
    private BattleRepository battleRepository;
    private BattleService battleService;

    @Before
    public void setUp() {
        battleRepository = new MemoryBattleRepository(new MemoryAccountRepository());
        battleService = new BattleService(battleRepository);
    }

    @Test
    public void getActiveBattlesForAccountId() {
        int accountId = 1;
        Battle[] battles = battleService.getActiveBattlesForAccountId(accountId);

        assertEquals(
                1,
                battles.length
        );
    }
}
