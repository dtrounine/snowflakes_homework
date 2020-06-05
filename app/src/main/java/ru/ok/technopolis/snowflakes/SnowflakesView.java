package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * TODO: Написать код, рисующий падающие снежинки
 */
public class SnowflakesView extends View {
    private int width;
    private int height;
    private Paint paint = new Paint();
    private SnowFlakes[] snowFlakes=new SnowFlakes[200];
    private Random random = new Random();

    public SnowflakesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = getWidth();
        height = getHeight();
        paint.setColor(Color.WHITE & 0x9fffffff);
        for (int i = 0; i < snowFlakes.length; i++) {
            snowFlakes[i] = new SnowFlakes();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        LetItSnow(canvas);
        invalidate();
    }

    private void LetItSnow(Canvas canvas){
        for(int i=0;i<snowFlakes.length;i++){
            canvas.drawCircle(snowFlakes[i].x,snowFlakes[i].y,snowFlakes[i].radius,paint);
            snowFlakes[i].refresh();
            if(snowFlakes[i].x - snowFlakes[i].radius > width || snowFlakes[i].y + snowFlakes[i].radius> height || snowFlakes[i].x + snowFlakes[i].radius < 0 ){
                snowFlakes[i]= new SnowFlakes();
            }
        }
    }

    private class SnowFlakes {
        private int x,y,radius;
        private int dx,dy;
        SnowFlakes(){
            this.x = random.nextInt(width);
            this.y = -100 + random.nextInt(100);
            this.dx = -3+random.nextInt(5);
            this.dy = 3+random.nextInt(6);
            this.radius = 5+ random.nextInt(15);
        }
        public void refresh(){
            x +=dx;
            y +=dy;
        }


    }



}
