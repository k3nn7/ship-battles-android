package net.lalik.shipbattles.offline.entity;

import java.util.ArrayList;
import java.util.List;

public class PlayerBattlefield {
    private List<Ship> inventory;

    public PlayerBattlefield() {
        inventory = new ArrayList<>();

        inventory.add(new Ship(1));
        inventory.add(new Ship(1));
        inventory.add(new Ship(2));
        inventory.add(new Ship(2));
        inventory.add(new Ship(3));
    }

    public List<Ship> getInventory() {
        return inventory;
    }
}
