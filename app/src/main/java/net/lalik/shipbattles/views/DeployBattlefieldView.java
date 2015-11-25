package net.lalik.shipbattles.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;

import java.util.ArrayList;
import java.util.List;

public class DeployBattlefieldView extends View {
    private int gridSize;
    private List<ShipView> shipViewList;
    private MyBattlefield battlefield = null;
    private Drawable horizontalShip;
    private Drawable verticalShip;

    private ShipView pickedShip = null;
    private Coordinate pickedCoordinate;

    private BattlefieldGridView battlefieldGridView;

    public DeployBattlefieldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        shipViewList = new ArrayList<>();
        horizontalShip = getResources().getDrawable(R.drawable.ship_horizontal);
        verticalShip = getResources().getDrawable(R.drawable.ship_vertical);
        battlefieldGridView = new BattlefieldGridView(5, 0);
    }

    private void initializeInventory() {
        int j = 1;
        for (ShipClass shipClass : battlefield.getAvailableShipClasses()) {
            for (int i = 0; i < battlefield.shipsCountInInventory(shipClass); i++) {
                ShipView shipView = new ShipView(
                        new Coordinate(i + 1, j),
                        Orientation.HORIZONTAL,
                        shipClass.getSize(),
                        verticalShip,
                        horizontalShip
                );
                shipView.resize(gridSize);
                shipViewList.add(shipView);
            }
            j++;
        }
    }

    public void setBattlefield(MyBattlefield battlefield) {
        this.battlefield = battlefield;
        initializeInventory();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        int smallestDimension = Math.min(width, height);
        gridSize = smallestDimension / 11;
        battlefieldGridView.resize(gridSize);
        for (ShipView shipView : shipViewList) {
            shipView.resize(gridSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        battlefieldGridView.draw(canvas);
        drawInventory(canvas);
    }

    private void drawInventory(Canvas canvas) {
        for (ShipView shipView : shipViewList) {
            shipView.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            handlePick((int) event.getX(), (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_MOVE)
            handleMove((int) event.getX(), (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_UP)
            handleDrop((int) event.getX(), (int) event.getY());

        return true;
    }

    private void handlePick(int x, int y) {
        for (ShipView shipView : shipViewList)
            if (shipView.contains(x, y)) {
                pickedShip = shipView;
                pickedCoordinate = pickedShip.getCoordinate();
            }
    }

    private void handleMove(int x, int y) {
        if (pickedShip != null){
            pickedShip.moveTo(positionToCoordinates(x, y));
            invalidate();
            requestLayout();
        }
    }

    private void handleDrop(int x, int y) {
        if (pickedShip == null)
            return;

        if (!battlefieldGridView.isInside(x, y)) {
            pickedShip.moveTo(pickedCoordinate);
            invalidate();
            requestLayout();
        }
        pickedShip = null;
    }

    public Coordinate positionToCoordinates(int x, int y) {
        return new Coordinate(
                (int) Math.floor(x / gridSize),
                (int) Math.floor(y / gridSize)
        );
    }
}
