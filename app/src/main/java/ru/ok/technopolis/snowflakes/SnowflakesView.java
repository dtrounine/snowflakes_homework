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
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private static final int FLAKES_NUMBER = 200;
    private static final int RADIUS_RATE = 50;
    private static final int SPEED_RATE = 3000;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final Paint paint = new Paint();

    private Coords[] snowFlakes;
    private int snowWidth;
    private int snowHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        snowWidth = w;
        snowHeight = h;
        snowFlakes = new Coords[FLAKES_NUMBER];
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            snowFlakes[i] = new Coords(random.nextInt(snowWidth), random.nextInt(snowHeight),
                    random.nextInt(snowWidth / RADIUS_RATE));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        wind();
        drawSnow(canvas);
        invalidate();
    }

    Random random = new Random();

    private void drawSnow(Canvas canvas) {
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            paint.setColor(Color.WHITE & 0x7fffffff);
            canvas.drawCircle(snowFlakes[i].getX(), snowFlakes[i].getY(), snowFlakes[i].getRad(), paint);
        }
    }

    private void wind() {
        int sing;
        int newX;
        int newY;
        for (int i = 0; i < FLAKES_NUMBER; i++) {
            if (i % 2 == 0) sing = -1;
            else sing = 1;
            newX = snowFlakes[i].getX() + sing * (snowWidth * snowFlakes[i].getRad()) / SPEED_RATE;
            newY = snowFlakes[i].getY() + (snowHeight * snowFlakes[i].getRad()) / SPEED_RATE;
            if (newY > snowHeight) newY = 0;
            if (newX > snowWidth || newX < 0) {
                if (random.nextBoolean()) {
                    newX = random.nextInt(snowWidth);
                    newY = 0;
                } else {
                    newY = random.nextInt(snowHeight);
                    if (sing == 1) newX = 0;
                    else newX = snowWidth - 1;
                }
            }
            snowFlakes[i] = new Coords(newX, newY, snowFlakes[i].getRad());
        }
    }
}
