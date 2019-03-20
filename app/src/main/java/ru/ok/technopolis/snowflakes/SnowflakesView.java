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

    private final Paint paint = new Paint();
    private final Random random = new Random();
    private static final int numOfFlakes = 200;
    private final int[] snowflakes = new int[numOfFlakes * 2];
    private final int[] radius = new int[numOfFlakes];
    private final int[] offset = new int[numOfFlakes * 2];
    private int width, height;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = getWidth();
        height = getHeight();
        paint.setARGB(200,255,255,255);
        for(int i = 0; i < numOfFlakes; i++) {
            createNewSnowFlake(i);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawAllSnowFlakes(canvas);
        invalidate();
    }

    private void createNewSnowFlake(int i) {
        snowflakes[i] = random.nextInt(width);
        snowflakes[i + numOfFlakes] = -50 + random.nextInt(50);
        offset[i] = -2 + random.nextInt(4);
        offset[i + numOfFlakes] = 2 + random.nextInt(6);
        radius[i] = 7 + random.nextInt(11);
    }

    private void drawAllSnowFlakes(Canvas canvas) {
        for (int i = 0; i < numOfFlakes; i++) {
            canvas.drawCircle(snowflakes[i], snowflakes[i + numOfFlakes], radius[i], paint);
            if (isInWindow(i)) {
                snowflakes[i] += offset[i];
                snowflakes[i + numOfFlakes] += offset[i + numOfFlakes];
            }
            else {
                createNewSnowFlake(i);
            }
        }
    }

    private boolean isInWindow(int i) {
        if (snowflakes[i] + offset[i] > width + radius[i]
                || snowflakes[i] + offset[i] < -radius[i]
                || snowflakes[i + numOfFlakes] + offset[i + numOfFlakes] > height + radius[i]) {
            return false;
        }
        return true;
    }
}
