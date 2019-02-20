package ru.ok.technopolis.snowflakes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

class SnowFlake {
    private final int flakeSize;
    private final Paint paint;
    private final Point pos;
    private final Random random;
    private int dx;
    private int dy;

    private static final int FLAKE_SIZE_UPPER = 20;

    SnowFlake(int flakeSize, Paint paint, Point pos, Random random) {
        this.flakeSize = flakeSize;
        this.paint = paint;
        this.random = random;
        this.pos= pos;
        this.dx = random.nextInt(5) - 2;
        this.dy = random.nextInt(5) + 2;
    }

    public static SnowFlake create(int w, int h, Paint paint) {
        Random random = new Random();
        int x = random.nextInt(w);
        int y = random.nextInt(100);
        Point pos = new Point (x, y);
        int flakeSize = random.nextInt(20) + 2;
        return new SnowFlake(flakeSize, paint, pos, random);
    }

    private void slide(int w, int h) {
        if (!isInFrame(w, h)) {
            relocate(w, h);
        }
        pos.x = pos.x + dx;
        pos.y = pos.y + dy;
    }

    private boolean isInFrame(int w, int h) {
        int x = pos.x;
        int y = pos.y;
        return ((x >= -flakeSize - 1) && (x + flakeSize <= w) && (y >= -flakeSize - 1) && (y - flakeSize < h));
    }

    private void relocate( int w, int h) {
       pos.x = random.nextInt(w);
       pos.y = 0;
        dx = random.nextInt(5) - 2;
        dy = random.nextInt(5) + 2;
    }

    public void draw(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        slide(w, h);
        canvas.drawCircle(pos.x, pos.y, flakeSize, paint);
    }
}
