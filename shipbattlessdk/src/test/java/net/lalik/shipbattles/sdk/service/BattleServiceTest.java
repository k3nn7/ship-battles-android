package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattleRepository;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BattleServiceTest {
    private AccountRepository accountRepository;
    private BattleRepository battleRepository;
    private BattleService battleService;

    @Before
    public void setUp() {
        accountRepository = new MemoryAccountRepository();
        battleRepository = new MemoryBattleRepository(accountRepository);
        battleService = new BattleService(battleRepository, accountRepository);
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

    @Test
    public void attackRandomOpponent() throws Exception {
        Battle battle1, battle2;
        Account attacker = accountRepository.findById(1);

        battle1 = battleService.attackRandomOpponent(attacker);
        battle2 = battleService.attackRandomOpponent(attacker);

        assertNotEquals(battle1.getId(), battle2.getId());
        assertNotEquals(attacker.getId(), battle1.getRightAccount());
        assertNotEquals(attacker.getId(), battle2.getRightAccount());
    }
}
