package com.njnu.kai.practice.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Choreographer;
import android.view.View;

import com.njnu.kai.support.LogUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/10
 */
public class MultiLineView extends View {

    private static final String TAG = "MultiLineView";

    private float aX, aY, bX, bY, angleA, angleB, speedA, speedB, aXR, aYR, bXR, bYR;

    private Paint mPaint;

    private Bitmap mBitmap;
    private Canvas canvasBitmap;

    private DrawFrameCallback mCallback;

    public MultiLineView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);
        mPaint = new Paint();
        mPaint.setColor(0x80FFFFFF);
        mPaint.setStyle(Paint.Style.FILL);
        speedA = 0.025f;
        speedB = 0.006f;
        aXR = 320;
        aYR = 320;
        bXR = 320;
        bYR = 80;
        mCallback = new DrawFrameCallback();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap == null) {
            return;
        }

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Choreographer.getInstance().postFrameCallback(mCallback);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Choreographer.getInstance().removeFrameCallback(mCallback);
    }

    private class DrawFrameCallback implements Choreographer.FrameCallback {

        @Override
        public void doFrame(long l) {
            if (mBitmap == null && getWidth() > 0 && getHeight() > 0) {
                mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                canvasBitmap = new Canvas(mBitmap);
                LogUtils.e(TAG, "create bitmap and canvas w=%d h=%d", getWidth(), getHeight());
            }
            if (canvasBitmap != null) {
                canvasBitmap.translate(getWidth() / 2, getHeight() / 2);
                angleA += speedA;
                angleB += speedB;
                aX = (float) (Math.cos(angleA) * aXR);
                aY = (float) (Math.sin(angleA) * aYR);
                bX = (float) (Math.cos(angleB) * bXR);
                bY = (float) (Math.sin(angleB) * bYR);
                canvasBitmap.drawLine(aX, aY, bX, bY, mPaint);

                invalidate();
            }

            Choreographer.getInstance().postFrameCallback(mCallback);
        }
    }
}
