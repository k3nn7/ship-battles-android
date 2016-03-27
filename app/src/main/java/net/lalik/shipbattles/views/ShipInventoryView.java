package net.lalik.shipbattles.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.offline.entity.Ship;
import net.lalik.shipbattles.sdk2.value.Coordinate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ShipInventoryView extends View {
    int gridSize;
    private Paint paint;
    private Drawable horizontalShip;
    private Drawable verticalShip;
    private List<Ship> inventory;
    private Map<Integer, ShipView> shipSizeViews;
    private Map<Integer, Integer> shipSizeCounts;
    private Map<Integer, Queue<ShipView>> inventoryViews;

    public ShipInventoryView(Context context) {
        super(context);
        initPaint();
        shipSizeCounts = new HashMap<>();
        shipSizeViews = new HashMap<>();
        inventoryViews = new HashMap<>();
        horizontalShip = getResources().getDrawable(R.drawable.ship_horizontal);
        verticalShip = getResources().getDrawable(R.drawable.ship_vertical);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setInventory(List<Ship> inventory) {
        this.inventory = inventory;
        initInventory();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Integer size : shipSizeCounts.keySet()) {
            int count = shipSizeCounts.get(size);
            canvas.drawText(
                    String.format("x %d", count),
                    (int) (gridSize * 3.5),
                    (int) (gridSize * size + (gridSize * 0.7)),
                    paint
            );

            shipSizeViews.get(size).draw(canvas);
        }
    }

    private void initInventory() {
        for (Ship ship : inventory) {
            Integer size = ship.getSize();
            if (!shipSizeCounts.containsKey(size)) {
                shipSizeCounts.put(size, 0);
                inventoryViews.put(size, new LinkedList<ShipView>());
                initShipViewForSize(size);
            }
            Integer count = shipSizeCounts.get(size);
            Queue<ShipView> shipViews = inventoryViews.get(size);

            shipSizeCounts.put(size, count + 1);
            shipViews.add(ShipView.fromShip(ship, verticalShip, horizontalShip));
        }
    }

    private void initShipViewForSize(Integer size) {
        ShipView shipView = ShipView.fromShip(
                new Ship(size),
                verticalShip,
                horizontalShip
        );
        shipView.moveTo(new Coordinate(0, size));
        shipView.resize(gridSize);
        shipSizeViews.put(size, shipView);
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        paint.setTextSize(gridSize / 2);
        for (ShipView shipView : shipSizeViews.values()) {
            shipView.resize(gridSize);
        }
    }

    public ShipView pick(int x, int y) {
        for (Integer size : shipSizeViews.keySet()) {
            if (shipSizeCounts.get(size) == 0)
                continue;

            ShipView view = shipSizeViews.get(size);
            if (view.contains(x, y)) {
                shipSizeCounts.put(size, shipSizeCounts.get(size) - 1);
                invalidate();
                return inventoryViews.get(size).remove();
            }
        }
        return null;
    }

    public void returnShip(ShipView shipView)  {
        int size = shipView.getSize();
        shipSizeCounts.put(size, shipSizeCounts.get(size) + 1);
        inventoryViews.get(size).add(shipView);
    }
}
