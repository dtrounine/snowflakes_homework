package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class SnowflakesView extends View {

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    class Circle {

        float x;
        float y;
        float r;
        float dx;
        float dy;

        Circle(float x, float y, float r, float dx, float dy) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.dx = dx;
            this.dy = dy;
        }

        void setX(float x) {
            this.x = x;
        }

        void setY(float y) {
            this.y = y;
        }

    }

    private int width;
    private int height;

    private LinkedList<Circle> snowflakes = new LinkedList<>();
    private Random random = new Random();
    private Paint paint = new Paint();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        spreadSnow();
        drawSnow(canvas);
        invalidate();
    }

    void spreadSnow() {

        ListIterator<Circle> iterator = snowflakes.listIterator();

        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            circle.setX(circle.x + circle.dx / 2 + random.nextInt(3) - 1);
            circle.setY(circle.y + circle.dy);
            if (circle.x < -10 || circle.x > width + 10 || circle.y > height + 10) {
                //Log.d("Remove", "Size - " + snowflakes.size() + " | Remove - " + ++count);
                iterator.remove();
            }
        }

        snowflakes.add(
                new Circle(
                        random.nextInt(width + 11) - 10,
                        0,
                        random.nextInt(8) + 5,
                        random.nextInt(11) - 5,
                        random.nextInt(6) + 5));

    }

    void drawSnow(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setAlpha(180);
        for (Circle circle :
                snowflakes) {
            canvas.drawCircle(circle.x, circle.y, circle.r, paint);
        }
    }

}
