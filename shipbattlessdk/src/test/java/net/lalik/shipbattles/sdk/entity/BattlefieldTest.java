package net.lalik.shipbattles.sdk.entity;

import net.lalik.shipbattles.sdk.values.AttackResult;
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
    ShipClass keel, destroyer;
    ShipsInventory shipsInventory;
    Battlefield battlefield;

    @Before
    public void setUp() {
        keel = new ShipClass(1, "Keel", 1);
        destroyer = new ShipClass(2, "Destroyer", 2);
        shipsInventory = new ShipsInventory();
        shipsInventory.addShips(keel, 2);
        shipsInventory.addShips(destroyer, 1);
        battlefield = new Battlefield(1, shipsInventory, new ArrayList<Battlefield.Ship>());
    }

    @Test
    public void deployExistingShip() throws Exception {
        battlefield.deployShip(new Coordinate(2, 3), Orientation.HORIZONTAL, keel);

        assertEquals(
                1,
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
        assertFalse(battlefield.isDeployed());

        battlefield.deployShip(new Coordinate(1, 3), Orientation.HORIZONTAL, keel);
        battlefield.deployShip(new Coordinate(2, 3), Orientation.HORIZONTAL, keel);
        battlefield.deployShip(new Coordinate(3, 3), Orientation.HORIZONTAL, destroyer);

        assertTrue(battlefield.isDeployed());
    }

    @Test
    public void attackMisses() {
        battlefield.deployShip(new Coordinate(1, 3), Orientation.HORIZONTAL, keel);
        assertEquals(
                AttackResult.MISS,
                battlefield.attack(new Coordinate(2, 3))
        );
    }

    @Test
    public void attackHit() {
        battlefield.deployShip(new Coordinate(1, 3), Orientation.HORIZONTAL, destroyer);
        assertEquals(
                AttackResult.HIT,
                battlefield.attack(new Coordinate(1, 3))
        );
    }

    @Test
    public void attackDestroys() {
        battlefield.deployShip(new Coordinate(1, 3), Orientation.HORIZONTAL, destroyer);
        battlefield.deployShip(new Coordinate(3, 3), Orientation.HORIZONTAL, keel);
        assertEquals(
                AttackResult.HIT,
                battlefield.attack(new Coordinate(1, 3))
        );
        assertEquals(
                AttackResult.DESTROYED,
                battlefield.attack(new Coordinate(1, 4))
        );

        assertEquals(
                AttackResult.DESTROYED,
                battlefield.attack(new Coordinate(3, 3))
        );
    }
}
