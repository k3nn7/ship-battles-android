package net.lalik.shipbattles.sdk.entity;

import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class BattlefieldTest {
    private Battlefield battlefield;

    @Test
    public void deployExistingShip() throws Exception {
        ShipClass keel = new ShipClass(1, "Keel", 1);
        ShipsInventory shipsInventory = new ShipsInventory();
        shipsInventory.addShips(keel, 3);
        battlefield = new Battlefield(1, shipsInventory, new ArrayList<Battlefield.Ship>());

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
}
