package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import static android.graphics.Color.WHITE;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private static final Random random = new Random();
    private Paint paint;
    private Snowflake[] snowflakes;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(WHITE);
        paint.setAlpha(200);
    }

    public static int calcRandom(int min, int max, boolean randomSign) {
        int sign = (randomSign ? (random.nextBoolean() ? 1 : -1) : 1);
        return (random.nextInt(max - min) + min) * sign;
    }

    private void createSnowFlakes(int n, int w, int h) {
        snowflakes = new Snowflake[n];
        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i] = new Snowflake(h, w);
        }
    }

    private void drawSnowflakes(Canvas canvas) {
        for (int i = 0; i < snowflakes.length; i++) {
            Snowflake s = snowflakes[i];

            canvas.drawCircle(s.getX(), s.getY(), s.getR(), paint);
            s.move();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        createSnowFlakes(200, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSnowflakes(canvas);
        invalidate();
    }

    class Snowflake {

        private int x, y, r, dx, dy, w, h;

        Snowflake(int h, int w) {
            this.w = w;
            this.h = h;
            init(false);
        }

        private void init(boolean restart) {
            x = random.nextInt(w);
            y = restart ? 0 : random.nextInt(h);
            r = calcRandom(5, 15, false);
            dx = calcRandom(1, 5, true);
            dy = calcRandom(3, 10, false);
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        int getR() {
            return r;
        }

        void move() {
            if (x < w && y < h) {
                x += dx;
                y += dy;
            } else {
                init(true);
            }
        }
    }
}
