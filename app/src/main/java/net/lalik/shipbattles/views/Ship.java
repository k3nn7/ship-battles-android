package net.lalik.shipbattles.views;

public class Ship {
    private final Coordinate coordinate;
    private final Orientation orientation;
    private final int size;

    public enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public enum Type {
        KEEL (1, "kuter"),
        FRIGATE (2, "fregata"),
        CRUISER (3, "krążownik"),
        DESTROYER (4, "pancernik");

        private final int size;
        private final String name;

        Type(int size, String name) {
            this.size = size;
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public String getName() {
            return name;
        }
    }

    public Ship(Coordinate coordinate, Orientation orientation, int size) {
        this.coordinate = coordinate;
        this.orientation = orientation;
        this.size = size;
    }

    public Coordinate getTopCoordinate() {
        return coordinate;
    }

    public Coordinate getBottomCoordinate() {
        int x = 0, y = 0;
        if (orientation == Orientation.HORIZONTAL) {
            x = coordinate.getX() + size;
            y = coordinate.getY() + 1;
        }
        if (orientation == Orientation.VERTICAL) {
            x = coordinate.getX() + 1;
            y = coordinate.getY() + size;
        }
        return new Coordinate(x, y);
    }
}
