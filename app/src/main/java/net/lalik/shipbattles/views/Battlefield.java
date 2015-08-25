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

public class Battlefield extends View {
    private boolean showGrid;
    private Paint textPaint, shipPaint;
    private int width, height, gridSize;

    public Battlefield(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.Battlefield,
                0,
                0
        );

        try {
            showGrid = attributes.getBoolean(R.styleable.Battlefield_showGrid, true);
        } finally {
            attributes.recycle();
        }

        initPaints();
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

        drawShip(canvas, new Coordinate(1, 1), new Coordinate(3, 2));
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

    private void drawShip(Canvas canvas, Coordinate start, Coordinate end) {
        float x1, y1, x2, y2;

        x1 = start.getX() * gridSize + gridSize;
        y1 = start.getY() * gridSize + gridSize;
        x2 = end.getX() * gridSize + gridSize;
        y2 = end.getY() * gridSize + gridSize;
        canvas.drawRect(x1, y1, x2, y2, shipPaint);
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
}
