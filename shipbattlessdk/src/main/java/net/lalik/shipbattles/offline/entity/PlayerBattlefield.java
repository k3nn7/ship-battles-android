package net.lalik.shipbattles.offline.entity;

import java.util.ArrayList;
import java.util.List;

public class PlayerBattlefield {
    private List<Ship> inventory;
    private List<Shot> shots;

    public PlayerBattlefield() {
        inventory = new ArrayList<>();
        shots = new ArrayList<>();

        inventory.add(new Ship(1));
        inventory.add(new Ship(1));
        inventory.add(new Ship(2));
        inventory.add(new Ship(2));
        inventory.add(new Ship(3));
    }

    public List<Shot> getShots() {
        return shots;
    }

    public List<Ship> getInventory() {
        return inventory;
    }

    public void addShot(Shot shot) {
        shots.add(shot);
        for (Ship ship : inventory)
            if (ship.isInside(shot.getCoordinate()))
                ship.addShot();
    }

    public int getDestroyedShipsCount() {
        int destroyedShipsCount = 0;
        for (Ship ship : inventory) {
            if (ship.isDestroyed())
                destroyedShipsCount++;
        }
        return destroyedShipsCount;
    }
}
