package ru.ok.technopolis.snowflakes;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SnowflakesView extends View {
    private final Random random = new Random();
    private ArrayList<Snowflake> snow;
    private int width;
    private int height;
    private final Paint paint = new Paint();
    private int tick = 0;


    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        snow = new ArrayList<>(200);
        width = w;
        height = h;
        paint.setColor(Color.rgb(203, 221, 255));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        snowIteration();
        drawSnow(canvas);
        invalidate();
    }

    private void drawSnow(Canvas canvas) {
        for (Snowflake snowflake: snow) {
            canvas.drawCircle(snowflake.getX(), snowflake.getY(), snowflake.getRadius(), paint);
        }
    }

    private boolean inFrame(Snowflake snowflake) {
        return !(snowflake.getY() >= height || snowflake.getX() <= 0 || snowflake.getX() >= width);
    }

    private void snowIteration() {
        for (int i = 0; i < snow.size(); i++) {
            snow.get(i).move(tick);
            if (!inFrame(snow.get(i))) snow.set(i, new Snowflake(random, width));
        }
        if (snow.size() < 200) {
            snow.add(new Snowflake(random, width));
        }
        tick++;
    }
}
