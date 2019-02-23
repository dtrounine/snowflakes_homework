package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Решил для разнообразия счделать типа дождь
 */
public class SnowflakesView extends View {

    private static final int FLAKES_NUMBER = 500;
    private static final int RADIUS_MAX = 800;
    private static final int SPEED_Y_MIN = 8;
    private static final int SPEED_Y_MAX = 30;
    private static final int SPEED_X_MAX = 8;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final Paint paint = new Paint();
    Random random = new Random();

    private Circle[] snowFlakes;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenWidth = w;
        screenHeight = h;
        snowFlakes = new Circle[FLAKES_NUMBER];
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            snowFlakes[i] = new Circle(
                    random.nextInt(screenWidth),
                    random.nextInt(screenHeight),
                    (int) Math.sqrt(random.nextInt(RADIUS_MAX)),
                    random.nextBoolean()
            );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        recalculate();
        drawSnow(canvas);
        invalidate();
    }

    private void drawSnow(Canvas canvas) {
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            paint.setColor(Color.WHITE & 0x7f1111ff);
            canvas.drawCircle(snowFlakes[i].getX(), snowFlakes[i].getY(), snowFlakes[i].getRad(), paint);
        }
    }

    private void recalculate() {
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            if (snowFlakes[i].getY() > screenHeight + 100)
                snowFlakes[i] = new Circle(
                        - 15 + random.nextInt(screenWidth + 30),
                        (int) - Math.sqrt(RADIUS_MAX),
                        (int) Math.sqrt(random.nextInt(RADIUS_MAX)),
                        random.nextBoolean()
                );
            else {
                snowFlakes[i].addX(random.nextInt(SPEED_X_MAX));
                snowFlakes[i].addY(SPEED_Y_MIN + random.nextInt(SPEED_Y_MAX - SPEED_Y_MIN));
            }
        }
    }

}
