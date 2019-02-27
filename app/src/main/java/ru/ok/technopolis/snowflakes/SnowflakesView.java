package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowflakesView extends View {

    private static final int NUM_FLAKES = 200;

    private List<SnowFlake> flakes = new ArrayList<>();
    private int windowWidth;
    private int windowHeight;

    private final Paint paint = new Paint();
    private final Random random = new Random();

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        windowWidth = w;
        windowHeight = h;

        for (int i = 0; i < NUM_FLAKES; i++) {
            int x = random.nextInt(windowWidth);
            int y = random.nextInt(windowHeight);
            flakes.add(new SnowFlake(x, y));
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFlakes(canvas);
        invalidate();
    }

    private void drawFlakes(Canvas canvas) {
        for (SnowFlake flake : flakes) {

            paint.setColor(Color.WHITE);
            paint.setAlpha(flake.getAlpha());
            canvas.drawCircle(flake.getX(), flake.getY(), flake.getWeight(), paint);

            flake.update();

            if (flake.getY() >= windowHeight || flake.getX() >= windowWidth || flake.getX() <= 0 ) {
                flake.setY(-flake.getWeight());
                flake.setX(random.nextInt(windowWidth));
            }
        }
    }

    private class SnowFlake {

        private static final int MIN_WEIGHT = 5;
        private static final int MAX_WEIGHT = 15;

        private static final int MIN_SPEED = 5;
        private static final int MAX_SPEED = 10;

        private static final int MIN_ALPHA = 50;
        private static final int MAX_ALPHA = 255;

        private int x;
        private int y;
        private int r;
        private double a;

        private int weight;
        private int speed;
        private int alpha;

        SnowFlake(int x, int y){
            this.x = x;
            this.y = y;
            this.r = random.nextInt(5);
            this.a = 0 + random.nextDouble() * Math.PI;

            this.weight = random.nextInt((MAX_WEIGHT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
            this.speed = Math.max(MIN_SPEED ,(this.weight / MAX_WEIGHT) * MAX_SPEED);
            this.alpha = MIN_ALPHA + random.nextInt(MAX_ALPHA - MIN_ALPHA + 1);
        }

        void update(){
            x += Math.cos(a) * r;
            y += speed;
        }

        int getX() {
            return x;
        }

        void setX(int x) {
            this.x = x;
        }

        int getY() {
            return y;
        }

        void setY(int y) {
            this.y = y;
        }

        int getR() {
            return r;
        }

        void setR(int r) {
            this.r = r;
        }

        double getA() {
            return a;
        }

        void setA(double a) {
            this.a = a;
        }

        int getWeight() {
            return weight;
        }

        void setWeight(int weight) {
            this.weight = weight;
        }

        int getAlpha() {
            return alpha;
        }

        void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        int getSpeed() {
            return speed;
        }

        void setSpeed(int speed) {
            this.speed = speed;
        }
    }


}
