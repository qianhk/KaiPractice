package com.njnu.kai.practice.animator.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.SDKVersionUtils;

/**
 * Created by kai
 * since 16/12/8
 */
public class CircleBkgView extends View {

    private static final String TAG = "CircleBkgView";
    private float mRadius;
    private float mRadiusY;
    private RectF mOvalRectF;
    private Paint mPaint;

    private int mDurationMs;
    private int mCurMs;

    private boolean mErrorMethod;

    private ValueAnimator mDurationValueAnimator;

    private static final boolean USE_HANDLER = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCurMs = msg.arg1;
            invalidate();
            nextMsUseHandler();
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
        mRadiusY = radius / 3;
        invalidate();
    }

    public void setErrorMethod(boolean errorMethod) {
        mErrorMethod = errorMethod;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtils.i(TAG, "look onDraw");
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStrokeWidth(DisplayUtils.dp2px(1));
            mPaint.setStyle(Paint.Style.STROKE);
        }
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        mPaint.setColor(0xFFBBCCDD);
        canvas.drawCircle(cx, cy, mRadius, mPaint);

        if (mOvalRectF == null) {
            mOvalRectF = new RectF(cx - mRadius, cy - mRadiusY, cx + mRadius, cy + mRadiusY);
        }
        mPaint.setColor(0xFFDDCCBB);
        canvas.drawOval(mOvalRectF, mPaint);

        mPaint.setColor(0xFF2222FF);

        int scx, scy;
        double step = 1.0 * mCurMs / mDurationMs * 2 * Math.PI;
        if (mErrorMethod) {
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
        } else {
            scx = (int) (cx - Math.cos(step) * mRadius);
            scy = (int) (cy - Math.sin(step) * mRadius);
        }
        canvas.drawCircle(scx, scy, DisplayUtils.dp2px(4), mPaint);

        mPaint.setColor(0xFFFF2222);
        scx = (int) (cx - Math.cos(step) * mRadius);
        scy = (int) (cy - Math.sin(step) * mRadiusY);
        canvas.drawCircle(scx, scy, DisplayUtils.dp2px(4), mPaint);
    }

    public void start(int durationMs) {
        mDurationMs = durationMs;
        mCurMs = 0;
        invalidate();
        if (USE_HANDLER) {
            nextMsUseHandler();
        } else {
            nextMsUseAnimator();
        }
    }

    private void nextMsUseHandler() {
        final int delayTime = 15;
        int newMs = mCurMs + delayTime;
        if (newMs > mDurationMs) {
            newMs = newMs - mDurationMs;
        }
        Message message = mHandler.obtainMessage(0, newMs, 0);
        mHandler.sendMessageDelayed(message, delayTime);
    }

    public void stop() {
        if (USE_HANDLER) {
            mHandler.removeCallbacksAndMessages(null);
        } else {
            if (mDurationValueAnimator != null && mDurationValueAnimator.isRunning()) {
                mDurationValueAnimator.cancel();
                mDurationValueAnimator = null;
            }
        }
    }

    private void nextMsUseAnimator() {
        stop();

        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mDurationMs);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            mCurMs = (int) valueAnimator1.getAnimatedValue();
            postInvalidateOnAnimationCompat();
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(mDurationMs);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();

        mDurationValueAnimator = valueAnimator;
    }

    private void postInvalidateOnAnimationCompat() {
        final long fakeFrameTime = 10;
        if (SDKVersionUtils.hasJellyBean()) {
            postInvalidateOnAnimation();
        } else {
            postInvalidateDelayed(fakeFrameTime);
        }
    }
}
