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

    private final Paint paint = new Paint();

    private final Random random = new Random();

    private final Snowflake[] snowflakes;

    private int width;
    private int height;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        snowflakes = new Snowflake[200];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = getWidth();
        height = getHeight();

        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i] = new Snowflake();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.WHITE & 0x9fffffff);

        for (Snowflake snowflake : snowflakes) {
            canvas.drawCircle(snowflake.x, snowflake.y, snowflake.radius, paint);
            snowflake.update();
        }

        invalidate();
    }

    private class Snowflake {

        private int x;

        private int y;

        private int radius;

        private int xSpeed;

        private int ySpeed;

        Snowflake() {
            this.radius = 16 - random.nextInt(12);
            this.xSpeed = 3 - random.nextInt(6);
            this.ySpeed = 1 + random.nextInt(6);
            this.x = random.nextInt(width - radius);
            this.y = - random.nextInt(height / 2);
        }

        void update() {
            x += xSpeed;
            y += ySpeed;

            if (y > height + radius) {
                y = -radius;
                x = random.nextInt(width - radius);
            }

            if (x == -radius) {
                x = width + radius;
            } else if (x == width + radius) {
                x = - radius;
            }
        }

    }
}
