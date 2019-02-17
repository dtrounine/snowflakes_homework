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
        for(Snowflake sf : snowflakes){
            sf.render(canvas);
        }
        deleteFallen();
        tryCreateSnow();
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
        int rnd = getRandomIntRange(-1,1);
        if (rnd > 0){
            snowflakes.add(new Snowflake());
        }
    }

    private void deleteFallen(){
        for (Iterator<Snowflake> iter = snowflakes.iterator(); iter.hasNext(); ){
            Snowflake sf = iter.next();
            if (sf.isFallen()){
                iter.remove();
            }
        }
    }

    private static int getRandomIntRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private class Snowflake{
        private int x,y,dx,dy,radius;
        public Snowflake(){
            radius = getRandomIntRange(1, 5);
            x = getRandomIntRange(radius, width - radius);
            y = 0;
            dx = getRandomIntRange(-3,3);
            dy = getRandomIntRange(2,5);
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
