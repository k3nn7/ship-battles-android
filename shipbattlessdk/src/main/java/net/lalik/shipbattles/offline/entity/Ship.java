package net.lalik.shipbattles.offline.entity;

import net.lalik.shipbattles.sdk2.value.Coordinate;

public class Ship {
    int size;
    int x;
    int y;
    int orientation;
    int shots = 0;

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

    public boolean isInside(Coordinate c) {
        if (orientation == 1) { // horizontal
            return (c.getX() >= this.x) && (c.getX() <= this.x + this.size - 1) && (c.getY() == this.y);
        }
        return (c.getY() >= this.y) && (c.getY() <= this.y + this.size - 1) && (c.getX() == this.x);
    }

    public void addShot() {
        shots++;
    }

    public boolean isDestroyed() {
        return shots >= size;
    }
}
