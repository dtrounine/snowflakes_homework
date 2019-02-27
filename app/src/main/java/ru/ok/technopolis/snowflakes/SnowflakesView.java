package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SnowflakesView extends View {

    private final Paint paint;
    private final Random random;
    private int width, height;
    private ArrayList<Snowflake> snow;
    private int time;
    private final Context context;


    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(60);
        random = new Random();
        time = Integer.MIN_VALUE;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        snow = new ArrayList<>();
    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        for (Snowflake snowflake : snow) {
            snowflake.draw(canvas);
        }
        paint.setColor(Color.YELLOW);
        canvas.drawText("Кол-dо снежинок: " + snow.size(), 20, 100, paint);
        refresh();
        invalidate();
    }

    private void generateSnow() {
        int n = random.nextInt(10) + 3;
        for (int i = 0; i < n; i++) {
            Snowflake snowflake = new Snowflake(random.nextInt(width), 0);
            snow.add(snowflake);
        }
    }

    private void deleteSnow() {
        Iterator<Snowflake> iterator = snow.iterator();
        while (iterator.hasNext()){
            Snowflake snowflake = iterator.next();
            if (snowflake.isDone()) {
                iterator.remove();
            }

        }
    }

    private void refresh() {
        time++;
        if (time % 7 == 0) {
            generateSnow();
        }
        deleteSnow();
    }

    class Snowflake {
        private int x, y, r, alpha, dx, dy;

        public Snowflake(int x, int y) {
            this.x = x;
            this.y = y;
            r = random.nextInt(10) + 5;
            alpha = random.nextInt(256) + 16;
            dy = random.nextInt(4) + 2;
            dx = random.nextInt(6) - 3;
        }

        boolean isDone() {
            return y > height + r || x < -r || x > width + r;
        }

        void draw(Canvas canvas) {
            paint.setAlpha(alpha);
            canvas.drawCircle(x, y, r, paint);
            move();
        }

        private void move() {
            x += dx;
            y += dy;
        }

    }

}
