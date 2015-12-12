package net.lalik.shipbattles.offline;

public class ShipBattles {
    private static ShipBattles instance = null;

    private ShipBattles() {

    }

    static public ShipBattles getInstance() {
        if (instance == null) {
            instance = new ShipBattles();
        }
        return instance;
    }
}
