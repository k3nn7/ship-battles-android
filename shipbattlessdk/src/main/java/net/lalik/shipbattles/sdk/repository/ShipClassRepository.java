package net.lalik.shipbattles.sdk.repository;

import net.lalik.shipbattles.sdk.entity.ShipClass;

public interface ShipClassRepository {
    ShipClass[] findAll();
}
