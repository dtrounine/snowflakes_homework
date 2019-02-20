package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private final Paint paint = new Paint();
    private int heightScreen;
    private int widthScreen;
    private List<Snowflake> snowFlakes = new LinkedList<>();
    private Random random = new Random();

    private class Snowflake {
        private Snowflake() {
            speedX = 0;
            speedY = 0;
            radius = 0;
            dstX = 0;
            dstY = 0;
        }
        int speedX, speedY;
        int radius;
        int dstX, dstY;
    }

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(0x7fFFFFFF);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSnowflakes(canvas);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        heightScreen = h;
        widthScreen = w;
    }

    private void initSnowflakes(Canvas canvas) {
        if (snowFlakes.size() == 0) {
            createSnowflakes(350);
        }

        if (snowFlakes.size() <= 250) {
            createSnowflakes(100);
        }
        ListIterator<Snowflake> iter = snowFlakes.listIterator();
        while (iter.hasNext()) {
            Snowflake s = iter.next();
            if (s.dstY == 0) {
                s.radius = random.nextInt(20) + 5;
                s.speedY = random.nextInt(15) + 1;
                switch (random.nextInt(3)) {
                    case 0: s.dstX = random.nextInt(widthScreen - 1) + 1;
                    s.dstY = 1;
                    s.speedX = random.nextInt(10) + 1;
                    if (random.nextInt(2) == 0) {
                        s.speedX = -s.speedX;
                    }
                    break;
                    case 1: s.dstY = random.nextInt( 3 * heightScreen / 4) + 1;
                    s.dstX = 1;
                    s.speedX = random.nextInt(10) + 1;
                    break;
                    case 2: s.dstY = random.nextInt( 3 * heightScreen / 4) + 1;
                    s.dstX = widthScreen - 1;
                    s.speedX = -(random.nextInt(10) + 1);
                    break;
                }
                iter.set(s);
            }
            else {
                s.dstX += s.speedX;
                s.dstY += s.speedY;
                if (s.dstX > widthScreen || s.dstX < 0 || s.dstY > heightScreen) {
                    iter.remove();
                    System.out.println("size: " + snowFlakes.size());
                }
                else {
                    iter.set(s);
                    canvas.drawCircle(s.dstX, s.dstY, s.radius, paint);
                }
            }
        }
    }

    private void createSnowflakes(int n) {
        for (int i = 0; i < n; i++) {
            snowFlakes.add(new Snowflake());
        }
    }
}
