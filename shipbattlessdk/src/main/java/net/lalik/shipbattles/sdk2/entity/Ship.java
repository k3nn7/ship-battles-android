package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;

public class Ship {
    @SerializedName("id")
    String id;
    @SerializedName("x")
    int x;
    @SerializedName("y")
    int y;
    @SerializedName("orientation")
    int orientation;

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getCoordinates() {
        return new Coordinate(x, y);
    }

    public Orientation getOrientation() {
        switch (orientation) {
            case 1:
                return Orientation.VERTICAL;
            case 2:
            default:
                return Orientation.HORIZONTAL;
        }
    }
}
