package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.util.CircularIntArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;

import java.util.Random;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int heightSnow;
    private int widthSnow;
    private final Paint paint=new Paint();
    private int[] coordinatesX;
    private int[] coordinatesY;
    private int[] radius;
    private int[] alpha;
    //private int countOfSnowflakes=1000; //fps 30
    //private int countOfSnowflakes=200; //fps 40
    private int countOfSnowflakes=500; //fps 40
    private Bitmap bitmap;
    private Random random=new Random();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float aspectRatio = 4;
        widthSnow=(int) (w/aspectRatio);
        heightSnow=(int)(h/aspectRatio);
        paint.setColor(Color.WHITE);
        coordinatesX=new int[countOfSnowflakes];
        coordinatesY=new int[countOfSnowflakes];
        radius=new int[countOfSnowflakes];
        alpha=new int[countOfSnowflakes];
        for (int n=0;n<countOfSnowflakes;n++){
            initializeCircle(n,random.nextInt(heightSnow));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bitmap=getSnowflakesBitmap();
        float scale = 4;
        canvas.scale(scale,scale);
        canvas.drawBitmap(bitmap,0,0,paint);
        invalidate();
    }

    private Bitmap getSnowflakesBitmap(){
        Bitmap bitmap=Bitmap.createBitmap(widthSnow,heightSnow, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for(int n=0;n<countOfSnowflakes;n++){
            paint.setAlpha(alpha[n]);
            canvas.drawCircle(coordinatesX[n],coordinatesY[n],radius[n],paint);
            coordinatesX[n]+=(random.nextInt(3)-1);
            coordinatesY[n]+=(random.nextInt(2));
            if (coordinatesX[n]<0||coordinatesX[n]>widthSnow||coordinatesY[n]>heightSnow){
                initializeCircle(n,0);
            }
        }
        return bitmap;
    }

    private void initializeCircle(int n,int startY){
        coordinatesX[n]=random.nextInt(widthSnow);
        coordinatesY[n]=startY;
        radius[n]=random.nextInt(2)+2;
        alpha[n]=191+random.nextInt(65);
    }
}
