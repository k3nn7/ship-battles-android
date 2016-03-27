package net.lalik.shipbattles.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.offline.entity.PlayerBattlefield;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;

import java.util.ArrayList;
import java.util.List;

public class OfflineDeployBattlefieldView extends View {
    private final int battlefieldOffset = 5;
    private int gridSize;
    private ShipInventoryView shipInventoryView;
    private List<ShipView> shipViewList;
    private PlayerBattlefield battlefield = null;
    private Drawable horizontalShip;
    private Drawable verticalShip;
    private ShipView pickedShip = null;
    private Coordinate pickedCoordinate;
    private BattlefieldGridView battlefieldGridView;
    private long lastTouchTime = 0;

    public OfflineDeployBattlefieldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        shipInventoryView = new ShipInventoryView(getContext());
        shipViewList = new ArrayList<>();
        horizontalShip = getResources().getDrawable(R.drawable.ship_horizontal);
        verticalShip = getResources().getDrawable(R.drawable.ship_vertical);
        battlefieldGridView = new BattlefieldGridView(battlefieldOffset, 0);
    }

    public void setBattlefield(PlayerBattlefield battlefield) {
        this.battlefield = battlefield;
        this.shipInventoryView.setInventory(battlefield.getInventory());
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        int smallestDimension = Math.min(width, height);
        gridSize = smallestDimension / 11;
        battlefieldGridView.resize(gridSize);
        for (ShipView shipView : shipViewList) {
            shipView.resize(gridSize);
        }
        this.shipInventoryView.setGridSize(gridSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        battlefieldGridView.draw(canvas);
        shipInventoryView.draw(canvas);

        for (ShipView ship : shipViewList) {
            ship.resize(gridSize);
            ship.draw(canvas);
        }

        if (pickedShip != null) {
            pickedShip.resize(gridSize);
            pickedShip.draw(canvas);
        }
    }

    public void updateOffset() {
        for (ShipView shipView : shipViewList) {
            Coordinate position = shipView.getCoordinate();
            shipView.moveTo(new Coordinate(position.getX() - battlefieldOffset, position.getY()));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isDoubleTouch(event.getEventTime()))
                handleDoubleTouch((int) event.getX(), (int) event.getY());
            handlePick((int) event.getX(), (int) event.getY());
            lastTouchTime = event.getEventTime();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE)
            handleMove((int) event.getX(), (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_UP)
            handleDrop((int) event.getX(), (int) event.getY());

        return true;
    }

    private boolean isDoubleTouch(long touchTime) {
        long delta = touchTime - lastTouchTime;
        return delta <= 250;
    }

    private void handlePick(int x, int y) {
        pickedShip = shipInventoryView.pick(x, y);

        for (ShipView ship : shipViewList)
            if (ship.contains(x, y))
                pickedShip = ship;


        if (pickedShip != null) {
            pickedCoordinate = pickedShip.getCoordinate();
            pickedShip.setLocalTransform(-5, -5);
            invalidate();
            requestLayout();
        }
    }

    private void handleDoubleTouch(int x, int y) {
        if (!battlefieldGridView.isInside(x, y))
            return;

        for (ShipView shipView : shipViewList)
            if (shipView.contains(x, y)) {
                if (shipView.getOrientation() == Orientation.VERTICAL)
                    shipView.setOrientation(Orientation.HORIZONTAL);
                else
                    shipView.setOrientation(Orientation.VERTICAL);
                invalidate();
                requestLayout();
            }
    }

    private void handleMove(int x, int y) {
        if (pickedShip != null) {
            pickedShip.moveTo(positionToCoordinates(x - gridSize, y));
            invalidate();
            requestLayout();
        }
    }

    private void handleDrop(int x, int y) {
        if (pickedShip == null)
            return;

        pickedShip.setLocalTransform(0, 0);

        if (!battlefieldGridView.isInside(x, y)) {
            if (shipViewList.contains(pickedShip))
                shipViewList.remove(pickedShip);
            shipInventoryView.returnShip(pickedShip);
            pickedShip = null;
            invalidate();
            return;
        }
        if (!shipViewList.contains(pickedShip))
            shipViewList.add(pickedShip);
        pickedShip = null;
        invalidate();
        requestLayout();
    }

    public Coordinate positionToCoordinates(int x, int y) {
        return new Coordinate(
                (int) Math.floor(x / gridSize),
                (int) Math.floor(y / gridSize)
        );
    }
}
