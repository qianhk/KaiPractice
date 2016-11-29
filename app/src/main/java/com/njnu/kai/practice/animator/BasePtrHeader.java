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

    private AnimatorSet mTopHideAnimatorSet;
    private AnimatorSet mTopShowAnimatorSet;

    private ObjectAnimator mBottomShowAlphaAnimator;
    private ObjectAnimator mBottomHideAlphaAnimator;

    private static final int ALPHA_ANIMATION_DURATION = 300;
    private ImageView mBkgImageView;

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

    private static final int HOLDER_VIEW_HEIGHT = DisplayUtils.dp2px(32);

    private void init(Context context) {

//        setPadding(0, DisplayUtils.dp2px(8), 0, DisplayUtils.dp2px(8));
        setBackgroundColor(Color.parseColor("#400000FF"));

        mBkgImageView = new ImageView(context);
        LayoutParams bkgParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bkgParams.topMargin = HOLDER_VIEW_HEIGHT;
        mBkgImageView.setImageResource(R.drawable.ic_ptr_bkg);
        addView(mBkgImageView, bkgParams);

        mTopTextView = new TextView(context);
        mTopTextView.setText("松手加载");
        mTopTextView.setTextColor(Color.BLACK);
        LayoutParams topTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topTextParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        topTextParams.topMargin = DisplayUtils.dp2px(12);
        addView(mTopTextView, topTextParams);

        mBottomTextView = new TextView(context);
        mBottomTextView.setText("加载中");
        mBottomTextView.setTextColor(Color.BLUE);
        LayoutParams bottomTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomTextParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
//        bottomTextParams.bottomMargin = DisplayUtils.dp2px(0);
        addView(mBottomTextView, bottomTextParams);


        ObjectAnimator topHideAlphaAnimator = ObjectAnimator.ofFloat(mTopTextView, "alpha", 1, 0);
//        ObjectAnimator topHideTranslationYAnimator = ObjectAnimator.ofFloat(mTopTextView, "translationY", 0);
        mTopHideAnimatorSet = new AnimatorSet();
        mTopHideAnimatorSet.playTogether(topHideAlphaAnimator);
        mTopHideAnimatorSet.setDuration(ALPHA_ANIMATION_DURATION);

        ObjectAnimator topShowAlphaAnimator = ObjectAnimator.ofFloat(mTopTextView, "alpha", 0, 1);
//        ObjectAnimator topShowTranslationYAnimator = ObjectAnimator.ofFloat(mTopTextView, "translationY", DisplayUtils.dp2px(8));
        mTopShowAnimatorSet = new AnimatorSet();
        mTopShowAnimatorSet.playTogether(topShowAlphaAnimator);
        mTopShowAnimatorSet.setDuration(ALPHA_ANIMATION_DURATION);

        mBottomShowAlphaAnimator = ObjectAnimator.ofFloat(mBottomTextView, "alpha", 0, 1);
        mBottomShowAlphaAnimator.setDuration(ALPHA_ANIMATION_DURATION);

        mBottomHideAlphaAnimator = ObjectAnimator.ofFloat(mBottomTextView, "alpha", 1, 0);
        mBottomHideAlphaAnimator.setDuration(ALPHA_ANIMATION_DURATION);

        onUIReset();
    }

    protected void onUIReset() {
//        LogUtils.i(TAG, "lookPtr onUIReset");
        mTopTextView.setVisibility(View.GONE);
        mBottomTextView.setVisibility(View.GONE);
        mTopTextView.setAlpha(0);
    }

    protected void onUIRefreshPrepare() {
//        LogUtils.i(TAG, "lookPtr onUIRefreshPrepare");
        mBkgImageView.setImageAlpha(0);
        mTopTextView.setVisibility(View.VISIBLE);
//        mTopTextView.setAlpha(0);

//        changeTextViewAlpha(mTopTextView, 0);
    }

    protected void onUIRefreshBegin() {
//        LogUtils.i(TAG, "lookPtr onUIRefreshBegin");
//        mTopTextView.setVisibility(View.GONE);
        mTopHideAnimatorSet.start();
        mBottomTextView.setVisibility(View.VISIBLE);
        mBottomShowAlphaAnimator.start();
        mLastLoadingPos = mLastLoadingPosTmp;
    }

    protected void onUIRefreshComplete() {
        LogUtils.i(TAG, "lookPtr onUIRefreshComplete");
        mBottomHideAlphaAnimator.start();
    }

    private int mLastLoadingPos;
    private int mLastLoadingPosTmp;

    protected void onUIPositionChange2(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int offsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        LogUtils.i(TAG, "lookPtr onUIPositionChange offsetToRefresh=%d curPos=%d lastPos=%d touch=%b status=%d"
                , offsetToRefresh, currentPos, lastPos, isUnderTouch, status);
        mLastLoadingPosTmp = currentPos;

        if (currentPos < offsetToRefresh && lastPos >= offsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch();
            }
        } else if (currentPos > offsetToRefresh && lastPos <= offsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch();
            }
        }
        if (isUnderTouch) {
            int alpha = 255;
            if (currentPos < offsetToRefresh) {
                float offsetRatio = 1.0f * currentPos / offsetToRefresh;
                alpha = (int) (offsetRatio * alpha);
            }
//            changeTextViewAlpha(mTopTextView, alpha);
            mBkgImageView.setImageAlpha(alpha);
        } else {
            if (status == PtrFrameLayout.PTR_STATUS_COMPLETE) {
                int alpha = 0;
                if (currentPos < offsetToRefresh) {
                    float offsetRatio = 1.0f * currentPos / offsetToRefresh;
                    alpha = (int) (offsetRatio * 255);
                }
//                changeTextViewAlpha(mTopTextView, alpha);
                mBkgImageView.setImageAlpha(alpha);
            } else if (status == PtrFrameLayout.PTR_STATUS_LOADING && currentPos >= offsetToRefresh) {
                float offsetRatio = 1.0f * (currentPos - offsetToRefresh) / (mLastLoadingPos - offsetToRefresh);
                mBkgImageView.setTranslationY(-HOLDER_VIEW_HEIGHT * offsetRatio);
            }
        }
    }

    private void changeTextViewAlpha(TextView textView, int alpha) {
        textView.setTextColor(textView.getTextColors().withAlpha(alpha));
    }

    private void crossRotateLineFromBottomUnderTouch() {
        LogUtils.i(TAG, "lookPtr 跨过 界限 向上 show_ani=%b hide_ani=%b"
                , mTopShowAnimatorSet.isRunning(), mTopHideAnimatorSet.isRunning());
        if (mTopShowAnimatorSet.isRunning()) {
            mTopShowAnimatorSet.end();
        }
        if (!mTopHideAnimatorSet.isRunning()) {
            mTopHideAnimatorSet.start();
        }
    }


    private void crossRotateLineFromTopUnderTouch() {
        LogUtils.i(TAG, "lookPtr 跨过 界限 向下 show_ani=%b hide_ani=%b"
                , mTopHideAnimatorSet.isRunning(), mTopShowAnimatorSet.isRunning());
        if (mTopHideAnimatorSet.isRunning()) {
            mTopHideAnimatorSet.end();
        }
        if (!mTopShowAnimatorSet.isRunning()) {
            mTopShowAnimatorSet.start();
        }
    }
}
