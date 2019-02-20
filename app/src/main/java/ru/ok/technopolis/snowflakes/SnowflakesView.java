package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

    public static final String TAG = SnowflakesView.class.getName();

    private final List<Snowflake> activeSnowflakes = new ArrayList<>();
    private final List<Snowflake> recycledSnowflakes = new ArrayList<>();

    private static final int MIN_NEW_SNOWFLAKES_PER_FRAME = 10;

    private final Random random = new Random();

    private final Paint snowflakePaint = new Paint();

    private long lastUpdateTime = 0;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        snowflakePaint.setColor(Color.WHITE);
        snowflakePaint.setStrokeWidth(1);
        snowflakePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        updateSnowflakesPositions();

        removeAllInvisibleSnowflakes();

        if (random.nextInt() % 5 == 0) {
            generateSnowflakes();
        }

        for (Snowflake snowflake : activeSnowflakes) {
            canvas.drawCircle(snowflake.x, snowflake.y, snowflake.radius, snowflakePaint);
        }

        invalidate();
    }

    private void updateSnowflakesPositions() {
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
        } else {
            long currentTime = System.currentTimeMillis();
            long deltaUpdateTime = currentTime - lastUpdateTime;
            float speedFactor = deltaUpdateTime / 200f;
            lastUpdateTime = currentTime;

            for (Snowflake snowflake : activeSnowflakes) {
                snowflake.x += (snowflake.speedX * speedFactor);
                snowflake.y += (snowflake.speedY * speedFactor);
            }
        }
    }

    private void removeAllInvisibleSnowflakes() {
        for (int i = 0; i < activeSnowflakes.size(); i++) {
            Snowflake snowflake = activeSnowflakes.get(i);
            if (snowflake.x > getMeasuredWidth()
                    || snowflake.x < 0
                    || snowflake.y > getMeasuredHeight()
            ) {
                recycledSnowflakes.add(activeSnowflakes.remove(i));
                i--;
            }
        }
    }

    private void generateSnowflakes() {
        for (int i = 0; i < MIN_NEW_SNOWFLAKES_PER_FRAME + random.nextInt(10); i++) {
            int x = random.nextInt(getMeasuredWidth());
            int y = 0;
            int radius = random.nextInt(30) + 5;
            int speedX = (int) (getMeasuredWidth() * ((random.nextInt(10) + 1) / 100f));
            int speedY = (int) (getMeasuredHeight() * ((random.nextInt(5) + 5) / 100f));

            if (random.nextInt() % 2 == 0) {
                speedX *= -1;
            }

            if (recycledSnowflakes.size() == 0) {
                activeSnowflakes.add(new Snowflake(x, y, radius, speedX, speedY));
            } else {
                Snowflake snowflake = recycledSnowflakes.remove(0);
                snowflake.x = x;
                snowflake.y = y;
                snowflake.radius = radius;
                snowflake.speedX = speedX;
                snowflake.speedY = speedY;
                activeSnowflakes.add(snowflake);
            }
        }
    }
}
