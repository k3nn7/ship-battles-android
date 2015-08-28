package net.lalik.shipbattles.sdk.values;

import net.lalik.shipbattles.sdk.entity.ShipClass;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShipsInventoryTest {
    @Test
    public void addShips() {
        ShipClass keel = new ShipClass(1, "Keel", 2);
        ShipsInventory inventory = new ShipsInventory();
        inventory.addShips(keel, 3);

        assertEquals(1, inventory.getInventory().length);
        assertEquals(keel, inventory.getInventory()[0].getShipClass());
        assertEquals(3, inventory.getInventory()[0].getCount());
    }

    @Test
    public void addMoreShips() {
        ShipClass keel = new ShipClass(1, "Keel", 2);
        ShipsInventory inventory = new ShipsInventory();
        inventory.addShips(keel, 3);
        inventory.addShips(keel, 1);
        assertEquals(4, inventory.getInventory()[0].getCount());
    }

    @Test
    public void pickExistingShip() throws Exception {
        ShipClass keel = new ShipClass(1, "Keel", 2);
        ShipsInventory inventory = new ShipsInventory();
        inventory.addShips(keel, 3);
        inventory.pickShip(keel);
        assertEquals(2, inventory.getInventory()[0].getCount());
    }
}
