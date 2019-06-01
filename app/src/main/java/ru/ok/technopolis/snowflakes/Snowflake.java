package ru.ok.technopolis.snowflakes;

public class Snowflake {
    private int x;
    private int y;
    private int radius;
    private Side side;

    public Snowflake(int x, int y, int radius, Side side) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.side = side;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void addX(int add) {
        switch (side) {
            case RIGHT:
                x += add;
                break;
            case LEFT:
                x -= add;
                break;
            default:
                System.out.println("Что-то не так...");
                break;
        }
    }

    public void addY(int add) {
        y += add;
    }

    enum Side {
        RIGHT, LEFT
    }
}
