package net.lalik.shipbattles.sdk.entity;

import net.lalik.shipbattles.sdk.values.AttackResult;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import java.util.ArrayList;

public class Battlefield {
    private int id;
    private int battleId;
    private ShipsInventory shipsInventory;
    private ArrayList<Ship> deployedShips;
    private ArrayList<Shot> shots;

    public Battlefield(int id, ShipsInventory shipsInventory, ArrayList<Ship> deployedShips) {
        this.id = id;
        this.shipsInventory = shipsInventory;
        this.deployedShips = deployedShips;
        this.shots = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
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

    public boolean isDeployed() {
        return shipsInventory.isEmpty();
    }

    public AttackResult attack(Coordinate coordinate) {
        shots.add(new Shot(coordinate));
        for (Ship ship : deployedShips)
            if (ship.within(coordinate))
                return ship.attack();
        return AttackResult.MISS;
    }

    public Shot[] getShots() {
        return shots.toArray(new Shot[shots.size()]);
    }

    public boolean isConquered() {
        for (Ship ship : deployedShips())
            if (!ship.isDestroyed())
                return false;
        return true;
    }

    public class Ship {
        private final Coordinate coordinate;
        private final Orientation orientation;
        private final ShipClass shipClass;
        private int hits;

        public Ship(Coordinate coordinate, Orientation orientation, ShipClass shipClass) {
            this.coordinate = coordinate;
            this.orientation = orientation;
            this.shipClass = shipClass;
            this.hits = 0;
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

        public boolean within(Coordinate coordinate) {
            if ((orientation == Orientation.HORIZONTAL) && (this.coordinate.getY() == coordinate.getY())) {
                return (coordinate.getX() >= this.coordinate.getX()) && (this.coordinate.getX() <= this.coordinate.getX() + shipClass.getSize());
            }

            if ((orientation == Orientation.VERTICAL) && (this.coordinate.getX() == coordinate.getX())) {
                return (coordinate.getY() >= this.coordinate.getY()) && (this.coordinate.getY() <= this.coordinate.getY() + shipClass.getSize());
            }
            return false;
        }

        public AttackResult attack() {
            this.hits++;
            if (hits >= shipClass.getSize()) {
                return AttackResult.DESTROYED;
            }
            return AttackResult.HIT;
        }

        public boolean isDestroyed() {
            return hits >= shipClass.getSize();
        }
    }

    public class Shot {
        private Coordinate coordinate;

        public Shot(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }
    }
}
