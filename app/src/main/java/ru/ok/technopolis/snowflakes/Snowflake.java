package ru.ok.technopolis.snowflakes;

public class Snowflake {

    public Snowflake(int x, int y, int radius, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    int x;
    int y;

    int radius;

    int speedX;
    int speedY;
}
