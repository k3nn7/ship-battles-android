package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Battlefield {
    @SerializedName("id")
    String id;
    @SerializedName("ready_for_battle")
    private boolean readyForBattle;
    @SerializedName("account_id")
    private String accountId;
    @SerializedName("shots")
    private List<Shot> shots;

    public String getId() {
        return id;
    }

    public boolean isReadyForBattle() {
        return readyForBattle;
    }

    public String getAccountId() {
        return accountId;
    }

    public List<Shot> getShots() {
        return shots;
    }
}
