package net.lalik.shipbattles.views;

import net.lalik.shipbattles.sdk.entity.ShipClass;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;

public class Ship {
    private final Coordinate coordinate;
    private final Orientation orientation;
    private final ShipClass shipClass;

    public Ship(Coordinate coordinate, Orientation orientation, ShipClass shipClass) {
        this.coordinate = coordinate;
        this.orientation = orientation;
        this.shipClass = shipClass;
    }

    public Coordinate getTopCoordinate() {
        return coordinate;
    }

    public Coordinate getBottomCoordinate() {
        int x = 0, y = 0;
        if (orientation == Orientation.HORIZONTAL) {
            x = coordinate.getX() + shipClass.getSize();
            y = coordinate.getY() + 1;
        }
        if (orientation == Orientation.VERTICAL) {
            x = coordinate.getX() + 1;
            y = coordinate.getY() + shipClass.getSize();
        }
        return new Coordinate(y, x);
    }
}
