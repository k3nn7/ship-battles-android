package net.lalik.shipbattles.fakeclient.repository;

import net.lalik.shipbattles.sdk.entity.ShipClass;
import net.lalik.shipbattles.sdk.repository.ShipClassRepository;

import java.util.ArrayList;

public class MemoryShipClassRepository implements ShipClassRepository {
    private ArrayList<ShipClass> shipClasses;

    public MemoryShipClassRepository() {
        shipClasses = new ArrayList<>();
        shipClasses.add(new ShipClass(1, "Kuter", 1));
        shipClasses.add(new ShipClass(2, "Fregata", 2));
        shipClasses.add(new ShipClass(2, "Krążownik", 3));
        shipClasses.add(new ShipClass(2, "Pancernik", 4));
    }

    @Override
    public ShipClass[] findAll() {
        return shipClasses.toArray(new ShipClass[shipClasses.size()]);
    }
}
