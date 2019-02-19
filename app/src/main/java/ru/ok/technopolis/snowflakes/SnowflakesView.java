package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    private final Paint paint = new Paint();
    private final Random random = new Random();
    private static final int color = 0xffF0F8FF;


    List<Snowflake> snowflakesList = new ArrayList<>();
    private int snowflakeWidth;
    private int snowflakeHeight;
    private int numOfSnowflake = 300;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        snowflakeWidth = w;
        snowflakeHeight = h;
        paint.setColor(color);

        for (int i = 0; i < numOfSnowflake; i++) {
            snowflakesList.add(new Snowflake(random.nextInt(snowflakeHeight)));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        fallingSnowflakes();
        drawSnowflakes(canvas);
        invalidate();
    }

    private void drawSnowflakes(Canvas canvas) {
        for (int i = 0; i < snowflakesList.size(); i++){
            Snowflake snowflake = snowflakesList.get(i);
            canvas.drawCircle(snowflake.positionX,snowflake.positionY,snowflake.radius, paint);
        }
    }

    private void fallingSnowflakes() {
        for (int i = 0; i < snowflakesList.size(); i++){
            Snowflake snowflake = snowflakesList.get(i);
            int rand_x = random.nextInt(3) + 1;
            int dist_x = snowflake.positionX;

            if (snowflake.direction > 0) {
                dist_x += rand_x;
            } else if (snowflake.direction < 0) {
                dist_x -= rand_x;
            }

            int dist_y = snowflake.positionY + snowflake.fallingSpeed;

            if (dist_x >= snowflakeWidth || dist_x <= 0 || dist_y >= snowflakeHeight) {
                snowflakesList.remove(snowflake);
                if (snowflakesList.size() < numOfSnowflake){
                    snowflakesList.add(new Snowflake(1));
                }
            } else {
                snowflake.setPositionX(dist_x);
                snowflake.setPositionY(dist_y);
                snowflakesList.set(i, snowflake);
            }
        }
    }

    protected class Snowflake {
        private int direction;
        private int radius;
        private int fallingSpeed;
        private int positionX;
        private int positionY;

        Snowflake(int y){
            direction = random.nextInt(3) - 1;
            radius = random.nextInt(15) + 4;
            fallingSpeed = random.nextInt(4) + 1;
            positionX = random.nextInt(snowflakeWidth);
            positionY = y;
        }

        void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        void setPositionY(int positionY) {
            this.positionY = positionY;
        }
    }

}
