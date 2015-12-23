package net.lalik.shipbattles.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.offline.entity.PlayerBattlefield;
import net.lalik.shipbattles.offline.entity.Ship;
import net.lalik.shipbattles.sdk2.value.Orientation;

import java.util.ArrayList;
import java.util.List;

public class OfflineMyBattlefieldView extends View {
    private int gridSize;
    private List<ShipView> shipViewList;
    private PlayerBattlefield battlefield = null;
    private Drawable horizontalShip;
    private Drawable verticalShip;
    private BattlefieldGridView battlefieldGridView;

    public OfflineMyBattlefieldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        shipViewList = new ArrayList<>();
        horizontalShip = getResources().getDrawable(R.drawable.ship_horizontal);
        verticalShip = getResources().getDrawable(R.drawable.ship_vertical);
        battlefieldGridView = new BattlefieldGridView(0, 0);
    }

    public void setBattlefield(PlayerBattlefield battlefield) {
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
        drawShips(canvas);
    }

    private void drawShips(Canvas canvas) {
        for (ShipView shipView : shipViewList) {
            shipView.draw(canvas);
        }
    }

    private void initializeInventory() {
        for (Ship ship : battlefield.getInventory())  {
            ShipView shipView = ShipView.fromShip(
                    ship,
                    verticalShip,
                    horizontalShip
            );

            shipView.resize(gridSize);
            shipViewList.add(shipView);
        }
    }
}
