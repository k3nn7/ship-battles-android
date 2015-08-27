package net.lalik.shipbattles.sdk.entity;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.sdk.repository.AccountRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleTest {
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        accountRepository = new MemoryAccountRepository();
    }

    @Test
    public void isActive() throws Exception {
        assertFalse(new Battle(
                accountRepository.findById(1),
                accountRepository.findById(2),
                Battle.STATE.FINISHED).isActive()
        );
        assertTrue(new Battle(
                        accountRepository.findById(1),
                        accountRepository.findById(2),
                        Battle.STATE.DEPLOY).isActive()
        );
        assertTrue(new Battle(
                        accountRepository.findById(1),
                        accountRepository.findById(2),
                        Battle.STATE.LEFT_ATTACKS).isActive()
        );
    }

    @Test
    public void isAccountIdParticipant() throws Exception {
        assertTrue(new Battle(
                        accountRepository.findById(1),
                        accountRepository.findById(2),
                        Battle.STATE.FINISHED).isAccountIdParticipant(1)
        );
        assertTrue(new Battle(
                        accountRepository.findById(1),
                        accountRepository.findById(2),
                        Battle.STATE.FINISHED).isAccountIdParticipant(2)
        );
        assertFalse(new Battle(
                        accountRepository.findById(1),
                        accountRepository.findById(2),
                        Battle.STATE.FINISHED).isAccountIdParticipant(3)
        );
    }

    @Test
    public void getSecondAccountId() throws Exception {
        assertEquals(accountRepository.findById(1), new Battle(
                accountRepository.findById(1),
                accountRepository.findById(2),
                Battle.STATE.FINISHED
        ).getSecondAccountId(2));
        assertEquals(accountRepository.findById(2), new Battle(
                accountRepository.findById(1),
                accountRepository.findById(2),
                Battle.STATE.FINISHED
        ).getSecondAccountId(1));
    }
}
