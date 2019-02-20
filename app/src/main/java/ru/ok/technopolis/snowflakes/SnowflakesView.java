package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SnowflakesView extends View {
    private final Paint paint = new Paint();
    private SnowFlake [] snowFlakes;
    private int width;
    private int height;
    private static int SNOWFLAKES_AMOUNT = 200;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        if (w != oldw || h != oldh) {
            paint.setColor(Color.WHITE);
            snowFlakes = new SnowFlake[SNOWFLAKES_AMOUNT];
            int i = 0;
            while (i < SNOWFLAKES_AMOUNT) {
                snowFlakes[i] = SnowFlake.create(w, h, paint);
                i++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SnowFlake snowFlake : snowFlakes) {
            snowFlake.draw(canvas);
        }
        invalidate();
    }
}
