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

    private final Snows[] snow;

    private int width, height;

    private final int OPASITY = 200;

    private final int MAX_RADIUS = 20;

    private final int SNOWS_COUNT = 250;

    private class Snows {

        private int opacity;

        private int x;

        private int y;

        private int radius;

        private int speed_x;

        private int speed_y;

        Snows() {
            this.radius = MAX_RADIUS - random.nextInt(16);
            this.speed_x = 3 - random.nextInt(6);
            this.speed_y = 1 + random.nextInt(6);
            this.x = random.nextInt(width - radius);
            this.y = - random.nextInt(height / 2);
            //this.y = 0;
            this.opacity = OPASITY - random.nextInt(100);
        }
    }

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        snow = new Snows[SNOWS_COUNT];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = getWidth();
        height = getHeight();

        for (int i = 0; i < SNOWS_COUNT; i++) {
            snow[i] = new Snows();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);

        for (Snows snowflake : snow) {

            paint.setAlpha(snowflake.opacity);

            canvas.drawCircle(snowflake.x, snowflake.y, snowflake.radius, paint);

            snowflake.x += snowflake.speed_x;
            snowflake.y += snowflake.speed_y;
            if(snowflake.y >= height+snowflake.radius){
                snowflake.y = -snowflake.radius;
                snowflake.x = random.nextInt(width-snowflake.radius);
            }

            /*if(snowflake.x > width+snowflake.radius || snowflake.x < -snowflake.radius){
                snowflake.y = -snowflake.radius;
                snowflake.x = random.nextInt(width- snowflake.radius);
            }*/

            if (snowflake.x == -snowflake.radius) {
                snowflake.x = width + snowflake.radius;
            } else if (snowflake.x == width + snowflake.radius) {
                snowflake.x = - snowflake.radius;
            }
        }

        invalidate();
    }



}
