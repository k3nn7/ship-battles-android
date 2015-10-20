package net.lalik.shipbattles.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.Ship;
import net.lalik.shipbattles.sdk2.value.Coordinate;

import java.util.Stack;

public class BattlefieldView extends View {
    private boolean showGrid;
    private Paint textPaint, shipPaint;
    private int width, height, gridSize;
    private Stack<Ship> ships;
    private MyBattlefield battlefield = null;
    private Drawable horizontalShip;
    private Drawable verticalShip;
    private boolean drawSelector = false;
    private Coordinate selectorPosition;
    private CoordinateSelectedListener coordinateSelectedListener;

    public interface CoordinateSelectedListener {
        void onCoordinateSelected(Coordinate coordinate);
    }

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

        horizontalShip = getResources().getDrawable(R.drawable.ship_horizontal);
        verticalShip = getResources().getDrawable(R.drawable.ship_vertical);

        selectorPosition = new Coordinate(0,0);
    }

    public void setBattlefield(MyBattlefield battlefield) {
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
        textPaint.setTextSize(gridSize / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLabels(canvas);
        drawGrid(canvas);

        if (null != battlefield) {
//            for (Ship ship : battlefield.getShips())
//                drawShip(canvas, ship);

//            for (Battlefield.Shot shot : battlefield.getShots())
//                drawShot(canvas, shot.getCoordinate());
        }

        if (drawSelector)
            drawSelection(canvas, selectorPosition);
    }

    private void drawLabels(Canvas canvas) {
        char [] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int i = 0; i < 10; i++) {
            canvas.drawText(
                    String.format("%d", i + 1),
                    3 * gridSize / 2 + (i * gridSize),
                    gridSize - 5,
                    textPaint
            );
        }

        for (int i = 0; i < 10; i++) {
            canvas.drawText(
                    String.format("%c", letters[i]),
                    gridSize / 2,
                    7 * gridSize / 4 + (gridSize * i),
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

//    private void drawShip(Canvas canvas, Ship ship) {
//        drawShip(
//                canvas,
//                ship.getCoordinates(),
//                getBottomCoordinate(ship),
//                ship.getOrientation()
//        );
//    }

//    private void drawShip(Canvas canvas, Coordinate start, Coordinate end, Orientation orientation) {
//        float x1, y1, x2, y2;
//
//        x1 = (start.getX() - 1) * gridSize + gridSize;
//        y1 = (start.getY() - 1) * gridSize + gridSize;
//        x2 = (end.getX() - 1) * gridSize + gridSize;
//        y2 = (end.getY() - 1) * gridSize + gridSize;
//
//        if (orientation == Orientation.HORIZONTAL) {
//            horizontalShip.setBounds((int) x1, (int) y1, (int) x2, (int) y2);
//            horizontalShip.draw(canvas);
//        } else {
//            verticalShip.setBounds((int) x1, (int) y1, (int) x2, (int) y2);
//            verticalShip.draw(canvas);
//        }
//    }

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
        textPaint.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
        textPaint.setTextAlign(Paint.Align.CENTER);

        shipPaint = new Paint(0);
        shipPaint.setColor(Color.rgb(0, 0, 0));
        shipPaint.setStyle(Paint.Style.STROKE);
        shipPaint.setStrokeWidth(4);
    }

//    public Coordinate getBottomCoordinate(Battlefield.Ship ship) {
//        int x = 0, y = 0;
//        if (ship.getOrientation() == Orientation.HORIZONTAL) {
//            x = ship.getCoordinate().getX() + ship.getShipClass().getSize();
//            y = ship.getCoordinate().getY() + 1;
//        }
//        if (ship.getOrientation()== Orientation.VERTICAL) {
//            x = ship.getCoordinate().getX() + 1;
//            y = ship.getCoordinate().getY() + ship.getShipClass().getSize();
//        }
//        return new Coordinate(y, x);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            selectorPosition.setX((int)event.getX());
//            selectorPosition.setY((int) event.getY());
//            drawSelector = true;
//            updateShots();
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            selectorPosition.setX((int)event.getX());
//            selectorPosition.setY((int)event.getY());
//            updateShots();
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            drawSelector = false;
//            updateShots();
//            if (coordinateSelectedListener != null) {
//                coordinateSelectedListener.onCoordinateSelected(new Coordinate(
//                        (int)Math.floor(event.getY() / gridSize) -1,
//                        (int)Math.floor(event.getX() / gridSize) -1
//                ));
//            }
//        }
//
//        return true;
//    }

    private void drawSelection(Canvas canvas, Coordinate coordinate) {
        float x1, y1, x2, y2;

        int x = (int)Math.floor(coordinate.getX() / gridSize) * gridSize;
        int y = (int)Math.floor(coordinate.getY() / gridSize) * gridSize;

        x1 = x;
        y1 = y;
        x2 = x + gridSize;
        y2 = y + gridSize;

        canvas.drawRect(x1 - gridSize, y1 - gridSize, x1, y1, shipPaint);
        canvas.drawLine((float) x1, 0, (float) x1, (float) height, shipPaint);
        canvas.drawLine((float)0, y1, (float)width, (float)y1, shipPaint);
    }

    public void setCoordinateSelectedListener(CoordinateSelectedListener coordinateSelectedListener) {
        this.coordinateSelectedListener = coordinateSelectedListener;
    }
}
