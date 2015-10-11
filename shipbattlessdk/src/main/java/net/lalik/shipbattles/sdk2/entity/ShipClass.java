package net.lalik.shipbattles.sdk2.entity;

import com.google.gson.annotations.SerializedName;

public class ShipClass {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("size")
    int size;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
