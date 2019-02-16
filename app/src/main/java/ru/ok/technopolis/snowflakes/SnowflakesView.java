package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

public class SnowflakesView extends View {

    private final Paint paint = new Paint();
    private final int[][] snowflakes = new int[200][2];
    private final int[][] delta = new int[snowflakes.length][2];
    private final int[] radius = new int[snowflakes.length];
    private int width, height;
    private final Random random = new Random();

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = getWidth();
        height = getHeight();
        paint.setARGB(175, 255, 255, 255);
        for (int i = 0; i < snowflakes.length; i++)
            createNewFlake(i);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        letItSnow(canvas);
        invalidate();
    }

    private void letItSnow(Canvas canvas) {
        for (int i = 0; i < snowflakes.length; i++) {
            canvas.drawCircle(snowflakes[i][0], snowflakes[i][1], radius[i], paint);
            snowflakes[i][0] += delta[i][0];
            snowflakes[i][1] += delta[i][1];
            if (snowflakes[i][0] + delta[i][0] > width + radius[i] || snowflakes[i][0] + delta[i][0] < -radius[i] || snowflakes[i][1] + delta[i][1] > height + radius[i])
                createNewFlake(i);

        }
    }

    private void createNewFlake(int i) {
        snowflakes[i][0] = random.nextInt(width);
        snowflakes[i][1] = -100 + random.nextInt(100);
        delta[i][0] = -2 + random.nextInt(4);
        delta[i][1] = 2 + random.nextInt(5);
        radius[i] = 5 + random.nextInt(10);
    }
}
