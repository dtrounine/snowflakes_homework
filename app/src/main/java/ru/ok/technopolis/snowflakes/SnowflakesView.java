package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.Iterator;

import android.view.View;
/**
 * TODODODO
 */
public class SnowflakesView extends View {

    private final Paint paint;
    private int width, height;
    private final int MIN_SNOWFLAKES = 250,
                      MAX_SNOWFLAKES_PER_FRAME = 13,
                      MIN_SNOWFLAKES_PER_FRAME = 5;
    ArrayList<Snowflake> snowflakes;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0xffffff);
        paint.setAlpha(128);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        for (Iterator<Snowflake> iterator = snowflakes.iterator(); iterator.hasNext(); ){
            Snowflake sf = iterator.next();
            if (sf.isFallen()){
                iterator.remove();
            } else {
                sf.render(canvas);
            }
        }

        tryCreateSnow();

        canvas.drawText(Integer.toString(snowflakes.size()),10,10,paint); // number of snowflakes on the screen

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        snowflakes = new ArrayList<>();
    }

    private void tryCreateSnow(){
        if (snowflakes.size() <= MIN_SNOWFLAKES){
            int count = getRandomIntRange(MIN_SNOWFLAKES_PER_FRAME,MAX_SNOWFLAKES_PER_FRAME);
            for (int i = 0; i < count; i ++){
                snowflakes.add(new Snowflake());
            }
        }
    }

    private static float getRandomFloatRange(float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (float) (Math.random() * ((max - min) + 1)) + min;
    }

    private static int getRandomIntRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private class Snowflake{
        private float x,y,dx,dy,radius;
        Snowflake(){
            radius = getRandomFloatRange(1, 5);
            x = getRandomFloatRange(radius, width - radius);
            y = 0;
            dx = getRandomFloatRange(-3,3);
            dy = getRandomFloatRange(2,4);
        }
        boolean isFallen(){
            return y >= height || x < 0 || x > width;
        }
        void render(Canvas canvas){
            canvas.drawCircle(x,y,radius, paint);
            x += dx; y += dy;
        }
    }
}
