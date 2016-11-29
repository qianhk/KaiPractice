package com.njnu.kai.practice.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.LogUtils;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by kai
 * since 16/11/28
 */
public class BasePtrHeader extends FrameLayout {

    private static final String TAG = "BasePtrHeader";
    private TextView mTopTextView;
    private TextView mBottomTextView;

    private AnimatorSet mTopHideAlphaAnimatorSet;
    private AnimatorSet mTopShowAlphaAnimatorSet;

    private static final int ALPHA_ANIMATION_DURATION = 300;

    public BasePtrHeader(Context context) {
        super(context);
        init(context);
    }

    public BasePtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BasePtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

//        setPadding(0, DisplayUtils.dp2px(8), 0, DisplayUtils.dp2px(8));
        setBackgroundColor(Color.parseColor("#400000FF"));

        ImageView bkgImageView = new ImageView(context);
        LayoutParams bkgParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bkgParams.topMargin = DisplayUtils.dp2px(12);
        bkgImageView.setImageResource(R.drawable.ic_ptr_bkg);
        addView(bkgImageView, bkgParams);

        mTopTextView = new TextView(context);
        mTopTextView.setText("松手加载");
        mTopTextView.setTextColor(Color.BLACK);
        LayoutParams topTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topTextParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//        topTextParams.topMargin = DisplayUtils.dp2px(0);
        addView(mTopTextView, topTextParams);

        mBottomTextView = new TextView(context);
        mBottomTextView.setText("加载中");
        mBottomTextView.setTextColor(Color.BLUE);
        LayoutParams bottomTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomTextParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
//        bottomTextParams.bottomMargin = DisplayUtils.dp2px(0);
        addView(mBottomTextView, bottomTextParams);


        ObjectAnimator topHideAlphaAnimator = ObjectAnimator.ofFloat(mTopTextView, "alpha", 0);
        ObjectAnimator topHideTranslationYAnimator = ObjectAnimator.ofFloat(mTopTextView, "translationY", 0);
        mTopHideAlphaAnimatorSet = new AnimatorSet();
        mTopHideAlphaAnimatorSet.playTogether(topHideAlphaAnimator, topHideTranslationYAnimator);
        mTopHideAlphaAnimatorSet.setDuration(ALPHA_ANIMATION_DURATION);


        ObjectAnimator topShowAlphaAnimator = ObjectAnimator.ofFloat(mTopTextView, "alpha", 1);
        ObjectAnimator topShowTranslationYAnimator = ObjectAnimator.ofFloat(mTopTextView, "translationY", DisplayUtils.dp2px(8));
        mTopShowAlphaAnimatorSet = new AnimatorSet();
        mTopShowAlphaAnimatorSet.playTogether(topShowAlphaAnimator, topShowTranslationYAnimator);
        mTopShowAlphaAnimatorSet.setDuration(ALPHA_ANIMATION_DURATION);

        onUIReset();
    }

    protected void onUIReset() {
//        LogUtils.i(TAG, "lookPtr onUIReset");
        mTopTextView.setVisibility(View.GONE);
        mBottomTextView.setVisibility(View.GONE);
    }

    protected void onUIRefreshPrepare() {
//        LogUtils.i(TAG, "lookPtr onUIRefreshPrepare");
        mTopTextView.setVisibility(View.VISIBLE);
        mTopTextView.setAlpha(0);
    }

    protected void onUIRefreshBegin() {
//        LogUtils.i(TAG, "lookPtr onUIRefreshBegin");
        mTopTextView.setVisibility(View.GONE);
        mBottomTextView.setVisibility(View.VISIBLE);
    }

    protected void onUIRefreshComplete() {
        LogUtils.i(TAG, "lookPtr onUIRefreshComplete");
//        mBottomTextView.startAnimation(mTopHideAlphaAnimator);
    }

    protected void onUIPositionChange2(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        LogUtils.i(TAG, "lookPtr onUIPositionChange offsetToRefresh=%d curPos=%d lastPos=%d touch=%b status=%d"
                , mOffsetToRefresh, currentPos, lastPos, isUnderTouch, status);

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch();
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch();
            }
        }
        if (!isUnderTouch && status == PtrFrameLayout.PTR_STATUS_COMPLETE && currentPos < mOffsetToRefresh) {
            mBottomTextView.setAlpha(currentPos / mOffsetToRefresh);
        }
    }

    private void crossRotateLineFromBottomUnderTouch() {
        LogUtils.i(TAG, "lookPtr 跨过 界限 向上 show_ani=%b hide_ani=%b"
                , mTopShowAlphaAnimatorSet.isRunning(), mTopHideAlphaAnimatorSet.isRunning());
        if (mTopShowAlphaAnimatorSet.isRunning()) {
            mTopShowAlphaAnimatorSet.end();
        }
        if (!mTopHideAlphaAnimatorSet.isRunning()) {
            mTopHideAlphaAnimatorSet.start();
        }
    }


    private void crossRotateLineFromTopUnderTouch() {
        LogUtils.i(TAG, "lookPtr 跨过 界限 向下 show_ani=%b hide_ani=%b"
                , mTopHideAlphaAnimatorSet.isRunning(), mTopShowAlphaAnimatorSet.isRunning());
        if (mTopHideAlphaAnimatorSet.isRunning()) {
            mTopHideAlphaAnimatorSet.end();
        }
        if (!mTopShowAlphaAnimatorSet.isRunning()) {
            mTopShowAlphaAnimatorSet.start();
        }
    }
}
