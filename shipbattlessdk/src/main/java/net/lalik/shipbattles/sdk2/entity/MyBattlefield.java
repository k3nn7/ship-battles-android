package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
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
    @SerializedName("ships")
    private List<Ship> ships;
    @SerializedName("shots")
    private List<Shot> shots;

    private ShipClass[] availableShipClasses;

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

    public void setAvailableShipClasses(ShipClass[] availableShipClasses) {
        this.availableShipClasses = availableShipClasses;
    }

    public ShipClass[] getAvailableShipClasses() {
        return this.availableShipClasses;
    }

    public int shipsCountInInventory(ShipClass shipClass) {
        return inventory.get(shipClass.getId()).intValue();
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<Shot> getShots() {
        return shots;
    }
}
