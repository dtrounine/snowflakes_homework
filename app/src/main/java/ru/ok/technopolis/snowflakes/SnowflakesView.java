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
 * Код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private static final int DX_MAX = 8;
    private static final int DY_MIN = 4;
    private static final int DY_MAX = 20;
    private static final int RADIUS_MIN = 2;
    private static final int RADIUS_MAX = 20;

    private final Random rand = new Random();
    private final Paint paint = new Paint();
    private final Snowflake[] snowflakes = new Snowflake[200];
    private int height;
    private int width;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(Color.WHITE);
        paint.setAlpha(128);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        height = h;
        width = w;
        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i] = randomSnowflake();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i].move();
            if (isOutOfScope(snowflakes[i])) {
                snowflakes[i] = randomSnowflake();
            }
            drawSnowflake(canvas, snowflakes[i]);
        }
        invalidate();
    }

    private Snowflake randomSnowflake() {
        int x = rand.nextInt(width);
        int y = 0;
        int radius = RADIUS_MIN + rand.nextInt(RADIUS_MAX - RADIUS_MIN);
        int dx = -DX_MAX + rand.nextInt(DX_MAX * 2);
        int dy = DY_MIN + rand.nextInt(DY_MAX - DY_MIN);
        return new Snowflake(x, y, dx, dy, radius);
    }

    private boolean isOutOfScope(Snowflake snowflake) {
        return snowflake.getY() > height || snowflake.getX() < 0 || snowflake.getX() > width;
    }

    private void drawSnowflake(Canvas canvas, Snowflake snowflake) {
        canvas.drawCircle(snowflake.getX(), snowflake.getY(), snowflake.getRadius(), paint);
    }
}
