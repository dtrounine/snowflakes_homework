package ru.ok.technopolis.snowflakes;

public class Snowflake {

    private int x;
    private int y;
    private final int dx;
    private final int dy;
    private final int radius;

    public Snowflake(int x, int y, int dx, int dy, int radius) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getRadius() {
        return radius;
    }

    public void move() {
        this.x += dx;
        this.y += dy;
    }
}
