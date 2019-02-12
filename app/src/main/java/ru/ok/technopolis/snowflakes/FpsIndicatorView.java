package ru.ok.technopolis.snowflakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

public class FpsIndicatorView extends View implements Handler.Callback {

    private static final int MSG_NEXT_FRAME = 1;

    private int frameCount;
    private long startTs;

    String fpsText;
    int fpsDigits;

    final Paint bgPaint = new Paint();
    final TextPaint textPaint = new TextPaint();
    final Rect textBounds = new Rect();
    final Handler handler = new Handler(this);

    public FpsIndicatorView(Context context) {
        this(context, null);
    }

    public FpsIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bgPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20 * context.getResources().getDisplayMetrics().density);
        textPaint.setColor(Color.YELLOW);
        textPaint.getTextBounds("WWWW", 0, 3, textBounds);
        startTs = SystemClock.uptimeMillis();
        handler.sendMessage(Message.obtain(handler, MSG_NEXT_FRAME));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getWidth();
        final int height = getHeight();
        final int textLeft = fpsDigits >= 4 ? 0
                : fpsDigits == 3 ? width / 4
                : fpsDigits == 2 ? width / 2
                : width * 3 / 4;
        canvas.drawRect(0, 0, width, height, bgPaint);
        if (fpsText != null) {
            canvas.drawText(fpsText, textLeft, height, textPaint);
        }
        frameCount++;
        invalidate();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == MSG_NEXT_FRAME) {
            final long ts = SystemClock.uptimeMillis();
            if (ts - startTs > 1000) {
                if (frameCount > 10) {
                    fpsText = Integer.toString(1000 * frameCount / (int) (ts - startTs));
                } else {
                    double fps = 1000.0 * frameCount / (ts - startTs);
                    fpsText = String.format(Locale.FRENCH, "%.2f", fps);
                }
                fpsDigits = fpsText.length();
                startTs = ts;
                frameCount = 0;
                invalidate();
            }
            handler.sendMessageDelayed(Message.obtain(handler, MSG_NEXT_FRAME), 100);
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(textBounds.width(), widthMeasureSpec),
                resolveSize(textBounds.height(), heightMeasureSpec));
    }
}
