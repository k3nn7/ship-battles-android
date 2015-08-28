package net.lalik.shipbattles.sdk.entity;

import java.util.Arrays;

public class Battle {
    private int id;
    private final Account leftAccount;
    private final Account rightAccount;
    public STATE state;

    public enum STATE {
        DEPLOY,
        LEFT_ATTACKS,
        RIGHT_ATTACKS,
        FINISHED
    }

    public Battle(Account leftAccount, Account rightAccount, STATE state) {
        this.leftAccount = leftAccount;
        this.rightAccount = rightAccount;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getLeftAccount() {
        return leftAccount;
    }

    public Account getRightAccount() {
        return rightAccount;
    }

    public STATE getState() {
        return state;
    }

    public boolean isActive() {
        STATE []activeStates = {STATE.DEPLOY, STATE.LEFT_ATTACKS, STATE.RIGHT_ATTACKS};
        return Arrays.asList(activeStates).contains(state);
    }

    public boolean isAccountIdParticipant(int accountId) {
        return ((leftAccount.getId() == accountId) || (rightAccount.getId() == accountId));
    }

    public Account getSecondAccountId(int firstAccount) {
        if (leftAccount.getId() == firstAccount) {
            return rightAccount;
        }
        return leftAccount;
    }

    public Account getCurrentAccount() {
        if (state == STATE.LEFT_ATTACKS) {
            return leftAccount;
        }
        return rightAccount;
    }
}
