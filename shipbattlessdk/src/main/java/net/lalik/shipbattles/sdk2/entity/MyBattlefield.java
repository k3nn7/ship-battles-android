package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class MyBattlefield {
    @SerializedName("id")
    String id;
    @SerializedName("ready_for_battle")
    private boolean readyForBattle;
    @SerializedName("account_id")
    private String accountId;
    @SerializedName("inventory")
    private Map<String, Integer> inventory;

    public String getId() {
        return id;
    }

    public boolean isReadyForBattle() {
        return readyForBattle;
    }

    public String getAccountId() {
        return accountId;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
