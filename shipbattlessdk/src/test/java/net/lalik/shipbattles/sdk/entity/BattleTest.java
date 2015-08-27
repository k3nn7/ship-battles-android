package net.lalik.shipbattles.sdk.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleTest {
    @Test
    public void isActive() {
        assertFalse(new Battle(1, 2, Battle.STATE.FINISHED).isActive());
        assertTrue(new Battle(1, 2, Battle.STATE.DEPLOY).isActive());
        assertTrue(new Battle(1, 2, Battle.STATE.LEFT_ATTACKS).isActive());
    }

    @Test
    public void isAccountIdParticipant() {
        assertTrue(new Battle(1, 2, Battle.STATE.FINISHED).isAccountIdParticipant(1));
        assertTrue(new Battle(1, 2, Battle.STATE.FINISHED).isAccountIdParticipant(2));
        assertFalse(new Battle(1, 2, Battle.STATE.FINISHED).isAccountIdParticipant(3));
    }

    @Test
    public void getSecondAccountId() {
        assertEquals(1, new Battle(1, 2, Battle.STATE.FINISHED).getSecondAccountId(2));
        assertEquals(2, new Battle(1, 2, Battle.STATE.FINISHED).getSecondAccountId(1));
    }
}
