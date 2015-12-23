package net.lalik.shipbattles.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class BattlefieldGridView {
    private final char [] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private final int offsetXCells;
    private final int offsetYCells;
    private int gridSize;
    private Paint paint;
    private Paint selectionPaint;
    private int x1, y1, x2, y2;
    private boolean showSelection = false;
    private int selectionX;
    private int selectionY;

    public BattlefieldGridView(int offsetXCells, int offsetYCells) {
        this.offsetXCells = offsetXCells;
        this.offsetYCells = offsetYCells;
        initializePaints();
    }

    public boolean isInside(int x, int y) {
        return (x > x1) && (x < x2) && (y > y1) && (y < y2);
    }

    private void initializePaints() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
        paint.setTextAlign(Paint.Align.CENTER);

        selectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectionPaint.setColor(Color.DKGRAY);
        selectionPaint.setStyle(Paint.Style.STROKE);
        selectionPaint.setStrokeWidth(4);
    }

    public void resize(int gridSize) {
        this.gridSize = gridSize;
        paint.setTextSize(gridSize / 2);
        x1 = gridSize + (offsetXCells * gridSize);
        y1 = gridSize + (offsetYCells * gridSize);
        x2 = gridSize * 11 + (offsetXCells * gridSize);
        y2 = gridSize * 11 + (offsetYCells * gridSize);
    }

    private int calculateGridSize(int width, int height) {
        int smallestDimension = Math.min(width, height);
        return smallestDimension / 11;
    }

    public void draw(Canvas canvas) {
        drawLabels(canvas);
        drawGrid(canvas);
        if (showSelection)
            drawSelection(canvas);
    }

    public void showSelection(boolean show) {
        this.showSelection = show;
    }

    public void setSelection(int x, int y) {
        this.selectionX = x;
        this.selectionY = y;
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 1; i < 12; i++) {
            drawVerticalLine(canvas, i);
            drawHorizontalLine(canvas, i);
        }
    }

    private void drawHorizontalLine(Canvas canvas, int i) {
        canvas.drawLine(
                x1,
                gridSize * i + (offsetYCells * gridSize),
                x2,
                gridSize * i + (offsetYCells * gridSize),
                paint
        );
    }

    private void drawVerticalLine(Canvas canvas, int i) {
        canvas.drawLine(
                gridSize * i + (offsetXCells * gridSize),
                y1,
                gridSize * i + (offsetXCells * gridSize),
                y2,
                paint
        );
    }

    private void drawLabels(Canvas canvas) {
        for (int i = 0; i < 10; i++) {
            drawHorizontalLabels(canvas, i);
            drawVerticalLabels(canvas, i);
        }
    }

    private void drawHorizontalLabels(Canvas canvas, int i) {
        canvas.drawText(
                String.format("%d", i + 1),
                3 * gridSize / 2 + (i * gridSize) + (offsetXCells * gridSize),
                gridSize - 5 + (offsetYCells * gridSize),
                paint
        );
    }

    private void drawVerticalLabels(Canvas canvas, int i) {
        canvas.drawText(
                String.format("%c", letters[i]),
                gridSize / 2 + (offsetXCells * gridSize),
                7 * gridSize / 4 + (gridSize * i) + (offsetYCells * gridSize),
                paint
        );
    }

    private void drawSelection(Canvas canvas) {
        int x = (int)Math.floor(selectionX / gridSize) * gridSize;
        int y = (int)Math.floor(selectionY / gridSize) * gridSize;

        canvas.drawRect(x - gridSize, y - gridSize, x, y, selectionPaint);
        canvas.drawLine((float) x, y1, (float) x, y2, selectionPaint);
        canvas.drawLine(x1, y, x2, (float) y, selectionPaint);
    }
}
