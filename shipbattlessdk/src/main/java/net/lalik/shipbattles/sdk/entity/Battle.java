package net.lalik.shipbattles.sdk.entity;

import java.util.Arrays;

public class Battle {
    private final int leftAccount;
    private final int rightAccount;
    private STATE state;

    public enum STATE {
        DEPLOY,
        LEFT_ATTACKS,
        RIGHT_ATTACKS,
        FINISHED
    }

    public Battle(int leftAccount, int rightAccount, STATE state) {
        this.leftAccount = leftAccount;
        this.rightAccount = rightAccount;
        this.state = state;
    }

    public int getLeftAccount() {
        return leftAccount;
    }

    public int getRightAccount() {
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
        return ((leftAccount == accountId) || (rightAccount == accountId));
    }

    public int getSecondAccountId(int firstAccount) {
        if (leftAccount == firstAccount) {
            return rightAccount;
        }
        return leftAccount;
    }
}
