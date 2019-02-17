package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private List <SnowFlake> snowflakes;
    private Paint paint;
    private Random random;
    private int width;
    private int height;
    private static final int NUMBER_OF_SNOWFLAKES = 8;
    private Timer timer;

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

        public boolean isDisappeared()
        {
            return y > height || x > width;
        }

        public void draw(Canvas canvas)
        {
            canvas.drawCircle(x,y,snowflake_radius,paint);
            x = x + deltaX;
            y = y + deltaY;
        }
    }

    public SnowflakesView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        timer = new Timer();
        paint = new Paint(Color.WHITE);
        paint.setARGB(175, 255, 255, 255);
        random = new Random();
        snowflakes = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        width = w;
        height = h;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        for(int i = 0; i < snowflakes.size();i++)
        {
            snowflakes.get(i).draw(canvas);
        }
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                updateSnowFlakes();
            }
        }, 7000);
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateSnowFlakes()
    {
        for(int i = 0; i < NUMBER_OF_SNOWFLAKES; i++)
        {
            snowflakes.add(new SnowFlake(random.nextInt(width), 0));
        }
        snowflakes.removeIf(SnowFlake::isDisappeared);
    }
}