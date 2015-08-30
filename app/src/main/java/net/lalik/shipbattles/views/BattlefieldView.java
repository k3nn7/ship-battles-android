package net.lalik.shipbattles.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;

import java.util.Stack;

public class BattlefieldView extends View {
    private boolean showGrid;
    private Paint textPaint, shipPaint;
    private int width, height, gridSize;
    private Stack<Ship> ships;
    private Battlefield battlefield = null;

    public BattlefieldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.BattlefieldView,
                0,
                0
        );

        try {
            showGrid = attributes.getBoolean(R.styleable.BattlefieldView_showGrid, true);
        } finally {
            attributes.recycle();
        }

        ships = new Stack<>();
        initPaints();
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public void updateShots() {
        invalidate();
        requestLayout();
    }

    public void deployShip(Ship ship) {
        ships.push(ship);
        invalidate();
        requestLayout();
    }

    public void clear() {
        ships.clear();
        invalidate();
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        this.width = width;
        this.height = height;

        int smallestDimension = Math.min(width, height);
        gridSize = smallestDimension / 11;
        textPaint.setTextSize(gridSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLabels(canvas);
        drawGrid(canvas);

        for (Ship ship : ships) {
            drawShip(canvas, ship.getTopCoordinate(), ship.getBottomCoordinate());
        }

        if (null != battlefield)
            for (Battlefield.Ship ship : battlefield.deployedShips()) {
                drawShip(canvas, ship);
            }

            for (Battlefield.Shot shot : battlefield.getShots())
                drawShot(canvas, shot.getCoordinate());
    }

    private void drawLabels(Canvas canvas) {
        char [] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int i = 0; i < 10; i++) {
            canvas.drawText(
                    String.format("%d", i + 1),
                    gridSize + (i * gridSize),
                    gridSize,
                    textPaint
            );
        }

        for (int i = 0; i < 10; i++) {
            canvas.drawText(
                    String.format("%c", letters[i]),
                    0,
                    gridSize + gridSize + (gridSize * i),
                    textPaint
            );
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 11; i++) {
            canvas.drawLine(
                    gridSize + (gridSize * i),
                    gridSize,
                    gridSize + (gridSize * i),
                    gridSize * 11,
                    textPaint
            );
        }

        for (int i = 0; i < 11; i++) {
            canvas.drawLine(
                    gridSize,
                    gridSize + (gridSize * i),
                    gridSize * 11,
                    gridSize + (gridSize * i),
                    textPaint
            );
        }
    }

    private void drawShip(Canvas canvas, Battlefield.Ship ship) {
        drawShip(
                canvas,
                ship.getCoordinate(),
                getBottomCoordinate(ship)
        );
    }

    private void drawShip(Canvas canvas, Coordinate start, Coordinate end) {
        float x1, y1, x2, y2;

        x1 = (start.getX() - 1) * gridSize + gridSize;
        y1 = (start.getY() - 1) * gridSize + gridSize;
        x2 = (end.getX() - 1) * gridSize + gridSize;
        y2 = (end.getY() - 1) * gridSize + gridSize;
        canvas.drawRect(x1, y1, x2, y2, shipPaint);
    }

    private void drawShot(Canvas canvas, Coordinate coordinate) {
        float x1, y1, x2, y2;
        x1 = (coordinate.getX() - 1) * gridSize + gridSize;
        x2 = x1 + gridSize;
        y1 = (coordinate.getY() - 1) * gridSize + gridSize;
        y2 = y1 + gridSize;
        canvas.drawLine(x1, y1, x2, y2, shipPaint);
        canvas.drawLine(x2, y1, x1, y2, shipPaint);
    }

    private void initPaints() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.rgb(0, 0, 0));
        textPaint.setTypeface(Typeface.MONOSPACE);

        shipPaint = new Paint(0);
        shipPaint.setColor(Color.rgb(255, 0, 0));
        shipPaint.setStyle(Paint.Style.STROKE);
        shipPaint.setStrokeWidth(4);
    }

    public Coordinate getBottomCoordinate(Battlefield.Ship ship) {
        int x = 0, y = 0;
        if (ship.getOrientation() == Orientation.HORIZONTAL) {
            x = ship.getCoordinate().getX() + ship.getShipClass().getSize();
            y = ship.getCoordinate().getY() + 1;
        }
        if (ship.getOrientation()== Orientation.VERTICAL) {
            x = ship.getCoordinate().getX() + 1;
            y = ship.getCoordinate().getY() + ship.getShipClass().getSize();
        }
        return new Coordinate(y, x);
    }
}