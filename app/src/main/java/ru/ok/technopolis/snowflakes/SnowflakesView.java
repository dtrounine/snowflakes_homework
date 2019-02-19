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
    private int width;
    private int height;
    private int size = 200;
    private int[] snowflakes_X = new int[size];
    private int[] snowflakes_Y = new int[size];
    private int[] delta_X = new int[size];
    private int[] delta_Y = new int[size];
    private int[] radius = new int[size];
    private final Random random = new Random();


    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = getWidth();
        height = getHeight();
        paint.setAlpha(175);
        paint.setColor(Color.WHITE);
        for (int i = 0; i < size; i++) {
            createNewSnowflakes(i);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        doSnow(canvas);
        invalidate();
    }

    private void doSnow(Canvas canvas) {
        for (int i = 0; i < size; i++) {
            snowflakes_X[i] += delta_X[i];
            snowflakes_Y[i] += delta_Y[i];
            canvas.drawCircle(snowflakes_X[i], snowflakes_Y[i], radius[i], paint);
            if (snowflakes_X[i] + delta_X[i] > width + radius[i] || snowflakes_Y[i] + delta_Y[i] > height + radius[i] || snowflakes_X[i] + delta_X[i] + radius[i] < 0) {
                createNewSnowflakes(i);
            }
        }

    }

    private void createNewSnowflakes(int i) {
        snowflakes_X[i] = random.nextInt(width);
        snowflakes_Y[i] = 0;
        delta_X[i]= -5 + random.nextInt(9);
        delta_Y[i]= 1 + random.nextInt(9);
        radius[i] = random.nextInt(10);
    }

}
