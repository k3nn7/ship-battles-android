package net.lalik.shipbattles.sdk.entity;

import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import java.util.ArrayList;

public class Battlefield {
    private int id;
    private ShipsInventory shipsInventory;
    private ArrayList<Ship> deployedShips;

    public Battlefield(int id, ShipsInventory shipsInventory, ArrayList<Ship> deployedShips) {
        this.id = id;
        this.shipsInventory = shipsInventory;
        this.deployedShips = deployedShips;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void deployShip(Coordinate coordinate, Orientation orientation, ShipClass shipClass) {
        shipsInventory.pickShip(shipClass);
        deployedShips.add(new Ship(coordinate, orientation, shipClass));
    }

    public ShipsInventory.Item[] shipsInInventory() {
        return shipsInventory.getInventory();
    }

    public Ship[] deployedShips() {
        return deployedShips.toArray(new Ship[deployedShips.size()]);
    }

    public class Ship {
        private final Coordinate coordinate;
        private final Orientation orientation;
        private final ShipClass shipClass;

        public Ship(Coordinate coordinate, Orientation orientation, ShipClass shipClass) {
            this.coordinate = coordinate;
            this.orientation = orientation;
            this.shipClass = shipClass;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        public ShipClass getShipClass() {
            return shipClass;
        }
    }
}
