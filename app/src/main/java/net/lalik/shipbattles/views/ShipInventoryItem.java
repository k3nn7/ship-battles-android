package net.lalik.shipbattles.views;

public class ShipInventoryItem {
    private final Ship.Type shipType;
    private final int quantity;

    public ShipInventoryItem(Ship.Type shipType, int quantity) {
        this.shipType = shipType;
        this.quantity = quantity;
    }

    public Ship.Type getShipType() {
        return shipType;
    }

    public int getQuantity() {
        return quantity;
    }
}
