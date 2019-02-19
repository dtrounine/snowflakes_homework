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

    private final Paint paint = new Paint();
    private final Random random = new Random();
    private final int length = 200;
    private int width, height;

    private class Snowflake {
        int x, y, dx, dy, radius, alpha;

        Snowflake() {
            x = random.nextInt(width);
            y = -random.nextInt(height);
            dx = -7 + random.nextInt(15);
            dy = 5 + random.nextInt(12);
            radius = 5 + random.nextInt(12);
            alpha = 50 + random.nextInt(150);
        }

        void move() {
            x = (x + dx + width) % (width + 1);
            y = (y + dy) % (height+1);
        }

        void draw(Canvas canvas) {
            paint.setAlpha(alpha);
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    private Snowflake[] snowflakes = new Snowflake[length];

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < length; i++) {
            snowflakes[i].draw(canvas);
            snowflakes[i].move();
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        for (int i = 0; i < length; i++) {
            snowflakes[i] = new Snowflake();
        }
    }
}
