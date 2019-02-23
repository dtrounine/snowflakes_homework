package ru.ok.technopolis.snowflakes;

class Circle {

    private int x;
    private int y;
    private int rad;
    private boolean side;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRad() {
        return rad;
    }

    public Circle(int x_coord, int y_coord, int rad, boolean side) {
        this.x = x_coord;
        this.y = y_coord;
        this.rad = rad;
        this.side = side;
    }

    public void addX(int add) {
        if (side)
            x += add;
        else
            x -= add;
    }

    public void addY(int add) {
        y += add;
    }

}
