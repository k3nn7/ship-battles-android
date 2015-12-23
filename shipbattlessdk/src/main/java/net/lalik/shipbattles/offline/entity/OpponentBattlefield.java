package net.lalik.shipbattles.offline.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OpponentBattlefield {
    private List<Ship> inventory;
    private List<Shot> shots;

    public OpponentBattlefield() {
        inventory = new ArrayList<>();
        shots = new ArrayList<>();

        inventory.add(new Ship(1));
        inventory.add(new Ship(1));
        inventory.add(new Ship(2));
        inventory.add(new Ship(2));
        inventory.add(new Ship(3));

        rotateShips();
        moveShips();
    }

    public List<Ship> getInventory() {
        return inventory;
    }

    private void rotateShips() {
        Random random = new Random();
        for (Ship ship : inventory) {
            ship.setOrientation(random.nextInt(2) + 1);
        }
    }

    private void moveShips() {
        Random random = new Random();
        for (Ship ship : inventory) {
            ship.setX(random.nextInt(10) + 1);
            ship.setY(random.nextInt(10) + 1);
        }
    }

    public void addShot(Shot shot) {
        shots.add(shot);
    }

    public List<Shot> getShots() {
        return shots;
    }
}
