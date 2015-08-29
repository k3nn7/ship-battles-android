package net.lalik.shipbattles.sdk.values;

import net.lalik.shipbattles.sdk.entity.ShipClass;

import java.util.HashMap;
import java.util.Map;

public class ShipsInventory {
    private HashMap<ShipClass, Integer> inventory;

    public ShipsInventory() {
        inventory = new HashMap<>();
    }

    public void addShips(ShipClass shipClass, int count) {
        if (inventory.containsKey(shipClass))
            count = inventory.remove(shipClass) + count;
        inventory.put(shipClass, count);
    }

    public void pickShip(ShipClass shipClass) {
        int countAfterPick = inventory.remove(shipClass) - 1;
        if (countAfterPick > 0)
            inventory.put(shipClass, countAfterPick);
    }

    public Item[] getInventory() {
        Item[] items = new Item[inventory.size()];
        int i = 0;
        for (Map.Entry<ShipClass, Integer> entry : inventory.entrySet()) {
            items[i] = new Item(entry.getKey(), entry.getValue());
            i++;
        }
        return items;
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public class Item {
        private final ShipClass shipClass;
        private final int count;

        public Item(ShipClass shipClass, int count) {
            this.shipClass = shipClass;
            this.count = count;
        }

        public ShipClass getShipClass() {
            return shipClass;
        }

        public int getCount() {
            return count;
        }
    }
}
