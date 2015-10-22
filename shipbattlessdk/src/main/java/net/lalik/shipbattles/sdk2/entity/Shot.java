package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

import net.lalik.shipbattles.sdk2.value.Coordinate;

public class Shot {
    @SerializedName("x")
    int x;
    @SerializedName("y")
    int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getCoordinates() {
        return new Coordinate(x, y);
    }
}
