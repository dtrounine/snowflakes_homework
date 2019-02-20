package ru.ok.technopolis.snowflakes;

public class Coords {

    private int x;
    private int y;
    private int rad;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRad() {
        return rad;
    }

    public Coords(int x, int y, int rad) {
        this.x = x;
        this.y = y;
        this.rad = rad;
    }


}
