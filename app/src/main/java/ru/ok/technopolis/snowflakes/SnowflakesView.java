package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private static final int SNOW_COLOR = 0xc4FFFFFF;
    private final Paint paint = new Paint();
    private final Random random = new Random();
    private int width;
    private int height;
    private Snowflake[] snowflakes = new Snowflake[250];

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i] = new Snowflake();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(SNOW_COLOR);

        for (Snowflake snowflake : snowflakes) {
            if (!snowflake.isAlive) {
                snowflake.isAlive = true;
                break;
            }
            snowflake.move();
            canvas.drawCircle(snowflake.x, snowflake.y, snowflake.radius, paint);
        }

        invalidate();
    }

    private class Snowflake {
        private static final int MIN_RADIUS = 5;
        private static final int MAX_RADIUS = 20;
        private static final int MIN_DX = 1;
        private static final int MAX_DX = 4;
        private static final int MIN_DY = 2;
        private static final int MAX_DY = 8;
        private int x;
        private int y;
        private int radius;
        private int dx;
        private int dy;
        private boolean isAlive = false;

        private Snowflake() {
            randomSpawn();
        }

        private void move() {
            x += dx;
            y += dy;
            if (y - radius > height || (x - radius > width && dx > 0) || (x + radius < 0 && dx < 0))
                randomSpawn();
        }

        private void randomSpawn() {
            radius = random.nextInt(MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;

            x = random.nextInt(width + height * 2) - height - width / 2;

            dx = random.nextInt(MAX_DX - MIN_DX) + MIN_DX;
            if (x >= width || x > 0 && random.nextBoolean()) {
                dx = -dx;
            }
            dy = Math.max(random.nextInt(MAX_DY - MIN_DY) + MIN_DY, dx);

            if (x + radius < 0) {
                int k = (-x - radius) / dx;
                y = -radius + dy * k;
                x = x + dx * k;
            } else if (x - radius > width) {
                int k = (width - x + radius) / dx;
                y = -radius + dy * k;
                x = x + dx * k;
            } else {
                y = -radius;
            }
        }
    }
}
