package com.njnu.kai.practice.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.DisplayUtils;

/**
 * Created by kai
 * since 16/11/25
 */
public class LoadingView extends FrameLayout {

    private TextPaint mTextPaint;
    private ImageView mWholeView;
    private ImageView mMouthView;
    private ImageView mOrganView;
    private AnimatorSet mAnimatorSet;
    private boolean mStarted;

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.CYAN);
        mTextPaint.setStrokeWidth(DisplayUtils.dp2px(1));

        mWholeView = new ImageView(context);
        mWholeView.setImageResource(R.drawable.ic_lo_header);
        mWholeView.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams wholeParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        wholeParams.bottomMargin = DisplayUtils.dp2px(6);
        addView(mWholeView, wholeParams);

        mMouthView = new ImageView(context);
        mMouthView.setImageResource(R.drawable.ic_lo_zui);
        mMouthView.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams mouthParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
//        mouthParams.topMargin = DisplayUtils.dp2px(2);
        addView(mMouthView, mouthParams);

        mOrganView = new ImageView(context);
        mOrganView.setImageResource(R.drawable.ic_lo_heart);
        mOrganView.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams organParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        organParams.bottomMargin = DisplayUtils.dp2px(8);
        addView(mOrganView, organParams);

    }

    private void prepareAnimator() {
        ObjectAnimator organViewTranslationY = ObjectAnimator.ofFloat(mOrganView, "translationY", -DisplayUtils.dp2px(3), DisplayUtils.dp2px(3), 0);
        organViewTranslationY.setInterpolator(new AccelerateDecelerateInterpolator());
        organViewTranslationY.setStartDelay(550);
        organViewTranslationY.setDuration(300);
//        organViewTranslationY.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator wholeViewTranslationY = ObjectAnimator.ofFloat(mWholeView, "translationY", 0, DisplayUtils.dp2px(6), 0);
        wholeViewTranslationY.setInterpolator(new AccelerateDecelerateInterpolator());
        wholeViewTranslationY.setStartDelay(700);
        wholeViewTranslationY.setDuration(300);
//        wholeViewTranslationY.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator mouthViewTranslationY = wholeViewTranslationY.clone();
        mouthViewTranslationY.setTarget(mMouthView);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(organViewTranslationY, wholeViewTranslationY, mouthViewTranslationY);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                mAnimatorSet = null;
                if (mStarted) {
                    animation.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                mAnimatorSet = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void start() {
        if (mAnimatorSet == null) {
            prepareAnimator();
            mAnimatorSet.start();
        }
        mStarted = true;
    }

    public boolean isStart() {
        return mStarted;
    }

    public void stop() {
        mStarted = false;
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
            mAnimatorSet = null;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mTextPaint == null) {
            return;
        }
//        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mTextPaint);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mTextPaint);
    }

}
