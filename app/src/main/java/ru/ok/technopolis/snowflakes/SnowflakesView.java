package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class SnowflakesView extends View {

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final static int SNOW_COUNT = 200;
    private final static @ColorInt
    int SNOW_COLOR = 0x80FFFFFF;

    private final Random random = new Random();
    private int[][] snowsCoors = new int[SNOW_COUNT][2];
    private int[] snowsRadius = new int[SNOW_COUNT];
    private int[] snowsSpeed = new int[SNOW_COUNT];
    private boolean[] snowsDirection = new boolean[SNOW_COUNT];
    private int snowWidth;
    private int snowHeight;
    private final Paint paint = new Paint();


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        snowWidth = w;
        snowHeight = h;
        paint.setColor(SNOW_COLOR);
        for (int x = 0; x < SNOW_COUNT; x++) {
            makeSnow(x, random.nextInt(snowWidth), random.nextInt(snowHeight));
        }
    }

    private void makeSnow(int snowNumber, int snowX, int snowY) {
        snowsCoors[snowNumber][0] = snowX;
        snowsCoors[snowNumber][1] = snowY;
        snowsRadius[snowNumber] = random.nextInt(25) + 5;
        snowsSpeed[snowNumber] = random.nextInt(4) + 1;
        snowsDirection[snowNumber] = random.nextBoolean();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        letItSnow(canvas);
        invalidate();
    }

    private void letItSnow(Canvas canvas) {
        for (int i = 0; i < SNOW_COUNT; i++) {
            canvas.drawCircle(snowsCoors[i][0], snowsCoors[i][1], snowsRadius[i], paint);
            if (snowsDirection[i]) {
                snowsCoors[i][0] += random.nextInt(3);
            } else {
                snowsCoors[i][0] += random.nextInt(3) - 2;
            }
            snowsCoors[i][1] += snowsSpeed[i];
            if (snowsCoors[i][0] > snowWidth || snowsCoors[i][0] < 0 || snowsCoors[i][1] > snowHeight) {
                makeSnow(i, random.nextInt(snowWidth), 0);
            }
        }
    }
}
