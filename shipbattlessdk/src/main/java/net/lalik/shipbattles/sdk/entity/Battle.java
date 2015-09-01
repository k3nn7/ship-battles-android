package net.lalik.shipbattles.sdk.entity;

import java.util.Arrays;

public class Battle {
    private int id;
    private final Account leftAccount;
    private final Account rightAccount;
    private int leftBattlefieldId;
    private int rightBattlefieldId;
    private STATE state;

    public int getBattlefieldIdForAccountId(int accountId) {
        if (leftAccount.getId() == accountId)
            return leftBattlefieldId;
        return rightBattlefieldId;
    }

    public enum STATE {
        DEPLOY,
        LEFT_ATTACKS,
        RIGHT_ATTACKS,
        FIRE_EXCHANGE,
        FINISHED
    }

    public Battle(
            Account leftAccount,
            Account rightAccount,
            int leftBattlefieldId,
            int rightBattlefieldId,
            STATE state
    ) {
        this.leftAccount = leftAccount;
        this.rightAccount = rightAccount;
        this.leftBattlefieldId = leftBattlefieldId;
        this.rightBattlefieldId = rightBattlefieldId;
        this.state = state;
    }

    public Battle(
            Account leftAccount,
            Account rightAccount,
            STATE state
    ) {
        this.leftAccount = leftAccount;
        this.rightAccount = rightAccount;
        this.state = state;
    }

    public void setLeftBattlefieldId(int leftBattlefieldId) {
        this.leftBattlefieldId = leftBattlefieldId;
    }

    public void setRightBattlefieldId(int rightBattlefieldId) {
        this.rightBattlefieldId = rightBattlefieldId;
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

    public int getLeftBattlefieldId() {
        return leftBattlefieldId;
    }

    public int getRightBattlefieldId() {
        return rightBattlefieldId;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public boolean isActive() {
        STATE []activeStates = {STATE.DEPLOY, STATE.LEFT_ATTACKS, STATE.RIGHT_ATTACKS, STATE.FIRE_EXCHANGE};
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
