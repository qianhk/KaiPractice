package com.njnu.kai.practice.animator.circle;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

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
    private ImageSwitcher mOrganView;
    private AnimatorSet mAnimatorSet;
    private boolean mStarted;

    private int[] mOrgans = {R.drawable.ic_lo_heart, R.drawable.ic_lo_eye, R.drawable.ic_lo_surprise};
    private int mIndex;

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

        mOrganView = new ImageSwitcher(context);
        mOrganView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                return imageView;
            }
        });
        mOrganView.setImageResource(mOrgans[0]);
        AlphaAnimation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(30);
        mOrganView.setInAnimation(inAnimation);
        AlphaAnimation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(30);
        mOrganView.setOutAnimation(outAnimation);
        LayoutParams organParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        organParams.bottomMargin = DisplayUtils.dp2px(8);
        addView(mOrganView, organParams);

    }

    private void prepareAnimator() {
        mIndex = 0;
        mOrganView.setImageResource(mOrgans[mIndex++]);
        ObjectAnimator organViewTranslationY = ObjectAnimator.ofFloat(mOrganView, "translationY", 0, -DisplayUtils.dp2px(3), DisplayUtils.dp2px(3));
        organViewTranslationY.setInterpolator(new AccelerateDecelerateInterpolator());
        organViewTranslationY.setDuration(300);
        organViewTranslationY.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mOrganView.setImageResource(mOrgans[mIndex++]);
                if (mIndex >= mOrgans.length) {
                    mIndex = 0;
                }
            }
        });

        ObjectAnimator organViewTranslationY2 = ObjectAnimator.ofFloat(mOrganView, "translationY", 0);
        organViewTranslationY2.setDuration(50);

        AnimatorSet organAnimatorSet = new AnimatorSet();
        organAnimatorSet.playSequentially(organViewTranslationY, organViewTranslationY2);


        ObjectAnimator wholeViewTranslationY = ObjectAnimator.ofFloat(mWholeView, "translationY", 0, DisplayUtils.dp2px(6), 0);
//        wholeViewTranslationY.setInterpolator(new AccelerateDecelerateInterpolator());
        wholeViewTranslationY.setStartDelay(150);
        wholeViewTranslationY.setDuration(300);

        ObjectAnimator mouthViewTranslationY = wholeViewTranslationY.clone();
        mouthViewTranslationY.setTarget(mMouthView);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setStartDelay(550);
        mAnimatorSet.playTogether(organAnimatorSet, wholeViewTranslationY, mouthViewTranslationY);
        mAnimatorSet.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                mAnimatorSet = null;
                if (mStarted) {
                    animation.start();
                }
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
