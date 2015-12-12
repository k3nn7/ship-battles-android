package net.lalik.shipbattles.offline.entity;

public class Ship {
    int size;
    int x;
    int y;
    int orientation;

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Ship(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
