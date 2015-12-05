package net.lalik.shipbattles.views;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;

public class ShipView {
    private Coordinate coordinate;
    private Orientation orientation;
    private final int size;
    private final Drawable verticalShip;
    private final Drawable horizontalShip;
    private int gridSize;
    private int x1, y1, x2, y2;
    private int localX, localY;

    public ShipView(
            Coordinate coordinate,
            Orientation orientation,
            int size,
            Drawable verticalShip,
            Drawable horizontalShip
    ) {
        this.coordinate = coordinate;
        this.orientation = orientation;
        this.size = size;
        this.verticalShip = verticalShip;
        this.horizontalShip = horizontalShip;
        this.localX = 0;
        this.localY = 0;
    }

    public void resize(int gridSize) {
        this.gridSize = gridSize;
        updateBoundary();
    }

    private void updateBoundary() {
        x1 = localX + coordinate.getX() * gridSize;
        y1 = localY + coordinate.getY() * gridSize;

        if (orientation == Orientation.HORIZONTAL) {
            x2 = x1 + size * gridSize;
            y2 = y1 + gridSize;
        } else {
            x2 = x1 + gridSize;
            y2 = y1 + size * gridSize;
        }
    }

    public void draw(Canvas canvas) {
        if (orientation == Orientation.HORIZONTAL) {
            horizontalShip.setBounds((int) x1, (int) y1, (int) x2, (int) y2);
            horizontalShip.draw(canvas);
        } else {
            verticalShip.setBounds((int) x1, (int) y1, (int) x2, (int) y2);
            verticalShip.draw(canvas);
        }
    }

    public boolean contains(int x, int y) {
        return (x > x1) && (x < x2) && (y > y1) && (y < y2);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void moveTo(Coordinate coordinate) {
        this.coordinate = coordinate;
        updateBoundary();
    }

    public void setLocalTransform(int x, int y) {
        localX = x;
        localY = y;
        updateBoundary();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
