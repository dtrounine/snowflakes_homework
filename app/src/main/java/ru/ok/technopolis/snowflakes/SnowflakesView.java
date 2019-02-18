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

    private static final int AMOUNT_OF_SNOWFLAKES = 282;

    private final Paint snowflakePaint = new Paint();
    private final Random random = new Random();
    private final Snowflake[] snowflakes = new Snowflake[AMOUNT_OF_SNOWFLAKES];

    private int width;
    private int height;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        snowflakePaint.setColor(Color.WHITE);
        snowflakePaint.setAlpha(160);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = getWidth();
        this.height = getHeight();
        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i] = new Snowflake(random.nextInt(width), random.nextInt(height));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Snowflake snowflake : snowflakes) {
            snowflake.move();
            canvas.drawCircle(snowflake.x, snowflake.y, snowflake.r, snowflakePaint);
        }
        invalidate();
    }

    class Snowflake {
        private int x;
        private int y;
        private int dx;
        private int dy;
        private int r;

        Snowflake(int x, int y) {
            this.x = x;
            this.y = y;
            setDeviationAndRadius();
        }

        void regen() {
            this.x = random.nextInt(width);
            this.y = 0;
            setDeviationAndRadius();
        }

        void setDeviationAndRadius() {
            this.dx = 5 - random.nextInt(10);
            this.dy = random.nextInt(5) + 7;
            this.r = random.nextInt(1) + 24;
        }

        void move() {
            x += dx;
            y += dy;
            if (x < 0 || x > width || y > height) { // center is not on screen
                regen();
            }
        }
    }
}
