package net.lalik.shipbattles.sdk.entity;

import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattlefieldTest {
    @Test
    public void deployExistingShip() throws Exception {
        ShipClass keel = new ShipClass(1, "Keel", 1);
        ShipsInventory shipsInventory = new ShipsInventory();
        shipsInventory.addShips(keel, 3);
        Battlefield battlefield = new Battlefield(1, shipsInventory, new ArrayList<Battlefield.Ship>());

        battlefield.deployShip(new Coordinate(2, 3), Orientation.HORIZONTAL, keel);

        assertEquals(
                2,
                battlefield.shipsInInventory()[0].getCount()
        );

        assertEquals(
                1,
                battlefield.deployedShips().length
        );

        assertEquals(
                keel,
                battlefield.deployedShips()[0].getShipClass()
        );
    }

    @Test
    public void isDeployedWhenAllShipsDeployed() throws Exception {
        ShipClass keel = new ShipClass(1, "Keel", 1);
        ShipsInventory shipsInventory = new ShipsInventory();
        shipsInventory.addShips(keel, 2);
        Battlefield battlefield = new Battlefield(1, shipsInventory, new ArrayList<Battlefield.Ship>());

        assertFalse(battlefield.isDeployed());

        battlefield.deployShip(new Coordinate(1, 3), Orientation.HORIZONTAL, keel);
        battlefield.deployShip(new Coordinate(2, 3), Orientation.HORIZONTAL, keel);

        assertTrue(battlefield.isDeployed());
    }
}
