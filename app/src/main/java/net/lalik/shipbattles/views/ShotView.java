package net.lalik.shipbattles.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import net.lalik.shipbattles.offline.entity.Shot;
import net.lalik.shipbattles.sdk2.value.Coordinate;

public class ShotView {
    private Coordinate coordinate;
    private int gridSize;
    private int x1, x2, y1, y2;
    private Paint paint;

    public ShotView(Coordinate coordinate) {
        this.coordinate = coordinate;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
    }

    public static ShotView fromShot(Shot shot) {
        return new ShotView(shot.getCoordinate());
    }

    public void resize(int gridSize) {
        this.gridSize = gridSize;
        updateBoundary();
    }

    private void updateBoundary() {
        x1 = coordinate.getX() * gridSize;
        y1 = coordinate.getY() * gridSize;

        x2 = x1 + gridSize;
        y2 = y1 + gridSize;
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(x1, y1, x2, y2, paint);
        canvas.drawLine(x1, y2, x2, y1, paint);
    }
}
