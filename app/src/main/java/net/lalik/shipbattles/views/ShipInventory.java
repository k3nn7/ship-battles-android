package net.lalik.shipbattles.views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ShipInventory {
    private HashMap<Ship.Type, Integer> items;

    public ShipInventory() {
        items = new HashMap<>();
    }

    public void addShips(Ship.Type type, int quantity) {
        if (items.containsKey(type)) {
            quantity += items.get(type);
        }
        items.put(type, quantity);
    }

    public void pickShip(Ship.Type type) {
        int quantity = items.remove(type) - 1;
        if (quantity > 0) {
            items.put(type, quantity);
        }
    }

    public Set<Map.Entry<Ship.Type, Integer>> availableShips() {
        return items.entrySet();
    }
}
