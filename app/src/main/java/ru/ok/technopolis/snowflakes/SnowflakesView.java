package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowflakesView extends View
{

    private List <SnowFlake> snowflakes;
    private Paint paint;
    private Random random;
    private int width;
    private int height;
    private static final int NUMBER_OF_SNOWFLAKES = 280;

    private final class SnowFlake
    {
        private int x;
        private int y;
        private int snowflake_radius;
        private int deltaX;
        private int deltaY;

        public SnowFlake(int CoordinateOnX, int CoordinateOnY)
        {
            this.snowflake_radius = random.nextInt(8) + 5;
            this.x = CoordinateOnX;
            this.y = CoordinateOnY;
            this.deltaX =  random.nextInt(6) - 2;
            this.deltaY = random.nextInt(4) + 2;
        }

        private boolean isDisappeared()
        {
            return y > height || x > width;
        }

        private void draw(Canvas canvas){
            canvas.drawCircle(x,y,snowflake_radius,paint);
            x = x + deltaX;
            y = y + deltaY;
        }

        private void reCreate() {
            this.snowflake_radius = random.nextInt(8) + 5;
            x = random.nextInt(width);
            this.y = 0;
            this.deltaX =  random.nextInt(6) - 2;
            this.deltaY = random.nextInt(4) + 2;
        }
    }

    public SnowflakesView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint(Color.WHITE);
        paint.setARGB(175, 255, 255, 255);
        random = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        width = w;
        height = h;
        snowflakes = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_SNOWFLAKES; i++)
        {
            snowflakes.add(new SnowFlake(random.nextInt(width), random.nextInt(100)));
        }
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        for(int i = 0; i < snowflakes.size();i++)
        {
            snowflakes.get(i).draw(canvas);
            if(snowflakes.get(i).isDisappeared())
                snowflakes.get(i).reCreate();
        }
        invalidate();
    }
}