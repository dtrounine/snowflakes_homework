package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class SnowflakesView extends View {
    private static final int FLAKES_NUMBER = 500;
    private static final int RADIUS_MAX = 600;
    private static final int SPEED_Y_MIN = 10;
    private static final int SPEED_Y_MAX = 20;
    private static final int SPEED_X_MAX = 8;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final Paint paint = new Paint();
    Random random = new Random();

    private Snowflake[] snowFlakes;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenWidth = w;
        screenHeight = h;
        snowFlakes = new Snowflake[FLAKES_NUMBER];
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            snowFlakes[i] = new Snowflake(
                    random.nextInt(screenWidth),
                    random.nextInt(screenHeight),
                    (int) Math.sqrt(random.nextInt(RADIUS_MAX)),
                    randomSide()
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
            paint.setColor(Color.WHITE);
            canvas.drawCircle(snowFlakes[i].getX(), snowFlakes[i].getY(), snowFlakes[i].getRadius(), paint);
        }
    }

    private void recalculate() {
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            if (snowFlakes[i].getY() > screenHeight + 100)
                snowFlakes[i] = new Snowflake(
                        -15 + random.nextInt(screenWidth + 30),
                        (int) -Math.sqrt(RADIUS_MAX),
                        (int) Math.sqrt(random.nextInt(RADIUS_MAX)),
                        randomSide()
                );
            else {
                snowFlakes[i].addX(random.nextInt(SPEED_X_MAX));
                snowFlakes[i].addY(SPEED_Y_MIN + random.nextInt(SPEED_Y_MAX - SPEED_Y_MIN));
            }
        }
    }

    private Snowflake.Side randomSide() {
        if (random.nextBoolean())
            return Snowflake.Side.RIGHT;
        else
            return Snowflake.Side.LEFT;
    }
}
