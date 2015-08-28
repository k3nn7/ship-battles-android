package net.lalik.shipbattles.sdk.entity;

public class ShipClass {
    private final int id;
    private final String name;
    private final int size;

    public ShipClass(int id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
