package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

public class Battle {
    @SerializedName("attacker_id")
    private String attackerId;
    @SerializedName("defender_id")
    private String defenderId;
    @SerializedName("id")
    private String id;
    @SerializedName("state")
    private int state;
    @SerializedName("my_battlefield")
    private MyBattlefield myBattlefield;
    @SerializedName("opponent_battlefield")
    private Battlefield opponentBattlefield;
    @SerializedName("turn_account_id")
    private String turnAccountId;

    public String getAttackerId() {
        return attackerId;
    }

    public String getDefenderId() {
        return defenderId;
    }

    public String getId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public MyBattlefield getMyBattlefield() {
        return myBattlefield;
    }

    public Battlefield getOpponentBattlefield() {
        return opponentBattlefield;
    }

    public String getTurnAccountId() {
        return turnAccountId;
    }
}
