package ru.ok.technopolis.snowflakes;

import java.util.Random;

import android.util.Log;

public class Snowflake {
    private int radius;
    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private double ampl;
    private double frec;
    private double phase;

    public int getRadius() {
        return radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private static class Consts {
        private static final int MIN_RADIUS = 7;
        private static final int MAX_RADIUS = 20;
        private static final int MIN_SPEED_X = -3;
        private static final int MAX_SPEED_X = 3;
        private static final int MIN_SPEED_Y = 4;
        private static final int MAX_SPEED_Y = 10;
    }

    Snowflake(Random random, int maxX) {
        radius = random.nextInt(Consts.MAX_RADIUS - Consts.MIN_RADIUS) + Consts.MIN_RADIUS;
        speedX = random.nextInt(Consts.MAX_SPEED_X - Consts.MIN_SPEED_X) + Consts.MIN_SPEED_X;
        speedY = random.nextInt(Consts.MAX_SPEED_Y - Consts.MIN_SPEED_Y) + Consts.MIN_SPEED_Y;

        double dSpeed = speed(Consts.MAX_SPEED_X, Consts.MAX_SPEED_Y) - speed(speedX, speedY);
        ampl =  1.3 * dSpeed * (1 - Math.exp(-3 * speedY));
        frec = random.nextDouble() * 3.6 * (Consts.MAX_SPEED_Y - speedY);
        phase = random.nextDouble() * 2 * Math.PI;

        x = random.nextInt(maxX);
        y = 0;

        Log.d("SNOW", "dX: " + speedX + " dY: " + speedY + " dS: " + dSpeed + " a: " + ampl);
    }

    public void move(int tick) {
        int dX = sin(tick);
        x += speedX + dX * (Math.signum(speedX) == Math.signum(dX) ? 1 : 1.3);
        y += speedY;
    }

    private int sin(int tick) {
        return (int) (ampl * Math.sin(frec * tick/1000 + phase));
    }

    private double speed(int x, int y) {
        return Math.sqrt(x*x + y*y);
    }
}
