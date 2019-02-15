package ru.ok.technopolis.snowflakes;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class SnowflakesView extends View {

    final Paint paint;
    final Random random;
    int width, height;
    ArrayList<Snowflake> snow;
    int time;


    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
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


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        snow.forEach(snowflake -> snowflake.draw(canvas));
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

    @TargetApi(Build.VERSION_CODES.N)
    private void deleteSnow() {
        snow.removeIf(Snowflake::isDone);
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
            dy = random.nextInt(4)+2;
            dx = random.nextInt(6) - 3;
        }

        boolean isDone() {
            return y > height;
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
