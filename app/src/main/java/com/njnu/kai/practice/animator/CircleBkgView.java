package com.njnu.kai.practice.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.njnu.kai.support.DisplayUtils;

/**
 * Created by kai
 * since 16/12/8
 */
public class CircleBkgView extends View {

    private float mRadius;
    private Paint mPaint;

    private int mDurationMs;
    private int mCurMs;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCurMs = msg.arg1;
            invalidate();
            nextMs();
        }
    };

    public CircleBkgView(Context context) {
        super(context);
    }

    public CircleBkgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleBkgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStrokeWidth(DisplayUtils.dp2px(1));
            mPaint.setStyle(Paint.Style.STROKE);
        }
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        mPaint.setColor(0xFFBBBBBB);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        mPaint.setColor(0xFFfa7829);

        int scx, scy;
        float xRatio;
        float halfDuration = mDurationMs / 2;
        if (mCurMs <= halfDuration) {
            xRatio = mCurMs / halfDuration;
        } else {
            xRatio = 1.0f - (mCurMs - halfDuration) / halfDuration;
        }
        scx = (int) (cx - mRadius + 2 * mRadius * xRatio);
        double sqrt = Math.sqrt(mRadius * mRadius - (scx - cx) * (scx - cx));
        if (mCurMs <= halfDuration) {
            sqrt = -sqrt;
        }
        scy = (int) (sqrt + cy);
        canvas.drawCircle(scx, scy, DisplayUtils.dp2px(4), mPaint);
}

    public void start(int durationMs) {
        mDurationMs = durationMs;
        mCurMs = 0;
        invalidate();
        nextMs();
    }

    private void nextMs() {
        final int delayTime = 50;
        int newMs = mCurMs + delayTime;
        if (newMs > mDurationMs) {
            newMs = newMs - mDurationMs;
        }
        Message message = mHandler.obtainMessage(0, newMs, 0);
        mHandler.sendMessageDelayed(message, delayTime);
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
