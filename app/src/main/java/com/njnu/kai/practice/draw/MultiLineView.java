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

    private float angleA, angleB, speedA, speedB, aXR, aYR, bXR, bYR;

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
//        aXR = 320;
//        aYR = 320;
//        bXR = 320;
//        bYR = 80;
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.e(TAG, "onSizeChanged w=%d h=%d", w, h);
        if (w > 0 && h > 0) {
            aXR = (float) (Math.min(w, h) * 0.4);
            aYR = aXR;
            bXR = aYR;
            bYR = bXR / 4;
            if (mBitmap != null) {
                mBitmap.recycle();
                mBitmap = null;
            }
        }
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

        private float aX, aY, bX, bY;

        @Override
        public void doFrame(long l) {
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            if (mBitmap == null && viewWidth > 0 && viewHeight > 0) {
                mBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
                canvasBitmap = new Canvas(mBitmap);
                canvasBitmap.translate(viewWidth >> 1, viewHeight >> 1);
                LogUtils.e(TAG, "create bitmap and canvas w=%d h=%d", viewWidth, viewHeight);
            }
            if (canvasBitmap != null) {
                angleA += speedA;
                angleB += speedB;
                aX = (float) (Math.cos(angleA) * aXR);
                aY = (float) (Math.sin(angleA) * aYR);
                bX = (float) (Math.cos(angleB) * bXR);
                bY = (float) (Math.sin(angleB) * bYR);
                canvasBitmap.drawLine(aX, aY, bX, bY, mPaint);
//               canvasBitmap.drawCircle(0, 0, 50 + angleA, mPaint);
                invalidate();
            }

            Choreographer.getInstance().postFrameCallbackDelayed(mCallback, 0);

        }
    }
}

//http://mp.weixin.qq.com/s?__biz=MzA4NTc5MDU5OQ==&mid=411441608&idx=1&sn=5e846a882f58a7ba1b5312bdbeaafccf&scene=23&srcid=0120GiYhMXjmNDoN9MFQj7f5#rd
