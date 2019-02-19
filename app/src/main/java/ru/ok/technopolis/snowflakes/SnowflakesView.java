package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SnowflakesView extends View {
    private Random random = new Random();
    ArrayList<Snowflake> listSnowflakes;
    private int width;
    private int height;
    private final int SIZE = 200;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        listSnowflakes = new ArrayList<>(SIZE);
    }

    private class Snowflake {
        int x, y, r, dx, dy;
        Paint paint = new Paint();

        Snowflake() {
            paint.setColor(Color.WHITE);
            paint.setAlpha(255 - random.nextInt(155));
            this.r = random.nextInt(15) + 7;
            this.dx = 4 - random.nextInt(6);
            this.dy = random.nextInt(6) + 2;
            this.x = random.nextInt(width - r);
            this.y = random.nextInt(height);
        }

        void repaint() {
            y += dy;
            x += dx;

            if (y > height) y = 0;
            if (x > width) x = 0;
            if (x < 0) x = width;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSnowflakes(canvas);
        invalidate();
    }


    private void drawSnowflakes(Canvas canvas) {
        for (int i = 0; i < SIZE; i++) {
            canvas.drawCircle(listSnowflakes.get(i).x, listSnowflakes.get(i).y,
                    listSnowflakes.get(i).r, listSnowflakes.get(i).paint);
            listSnowflakes.get(i).repaint();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
        for (int i = 0; i < SIZE; i++)
            listSnowflakes.add(new Snowflake());
    }
}
