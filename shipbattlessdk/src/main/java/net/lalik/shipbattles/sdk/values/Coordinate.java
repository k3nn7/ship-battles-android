package net.lalik.shipbattles.sdk.values;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != Coordinate.class)
            return false;
        Coordinate coordinate = (Coordinate)other;
        return (coordinate.getX() == x) && (coordinate.getY() == y);
    }
}
