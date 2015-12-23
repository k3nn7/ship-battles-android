package net.lalik.shipbattles.offline.entity;

import net.lalik.shipbattles.sdk2.value.Coordinate;

public class Shot {
    private Coordinate coordinate;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Shot(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
