package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {
    private static  int[] snowX = new int[200] ;
    private static  int[] snowY = new int[200] ;
    private static  int[] snowN = new int[200] ;
    private static  int[] snowSpeed = new int[200];
    private static  int[] snowSize = new int[200];
    private static  int[] snowSing = new int[200];
    private final Random random = new Random();
    private int snowWidth;
    private int snowHeight;

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        snowWidth = w;
        snowHeight = h;

        int sizySnow = snowHeight/400;
        for (int i=0;i<snowSize.length;i++){
            snowN[i]=0;
            snowY[i]=random.nextInt(snowHeight);
            snowX[i]=random.nextInt(snowWidth);
            snowSize[i]=(random.nextInt(6)+1)*sizySnow;
            snowSpeed[i]=random.nextInt(3);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x00000000);

        for (int i=0 ;i < snowSize.length;i++){

            if (random.nextBoolean())
                snowN[i]++;

            if (snowY[i]>snowHeight) {
                snowX[i]=random.nextInt(snowWidth);
                snowY[i]=0;
                snowN[i]=0;
            }

            if (snowN[i]%20 == 0 ) {
                snowSpeed[i] += 1-random.nextInt(2);
                if (snowSpeed[i] > 3) snowSpeed[i] = 3;
                if (snowSpeed[i] < 0) snowSpeed[i] = 1;
            }
            if (snowN[i]%40 == 0) snowSing[i] = (random.nextBoolean()) ? 1 : -1;

            snowY[i] += snowSpeed[i]+snowSize[i]/2;
            snowX[i] += snowSing[i]*(snowSpeed[i]);

            Paint paint = new Paint();
            paint.setColor(0xffffffff);
            canvas.drawCircle(snowX[i], snowY[i],snowSize[i], paint);
        }
        invalidate();
    }
}
