package com.njnu.kai.practice.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.DisplayUtils;

/**
 * Created by kai
 * since 16/11/25
 */
public class LoadingAnimatorFragment extends BaseTestFragment {

    private static final String TAG = "LoadingAnimatorFragment";

    private LoadingView mLoadingView;
    private Button mBtnActionLoading;

    private AnimatorSet mAnimatorSet;

    private void flushLoadingActionText() {
        mBtnActionLoading.setText(mLoadingView.isStart() ? "停止" : "开始");
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_loading_animator, viewGroup, false);
        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading_view);
        mBtnActionLoading = (Button) rootView.findViewById(R.id.btn_action_loading);
        mLoadingView.start();
        flushLoadingActionText();
        mBtnActionLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadingView.isStart()) {
                    mLoadingView.stop();
                } else {
                    mLoadingView.start();
                }
                flushLoadingActionText();
            }
        });
        View pointView = rootView.findViewById(R.id.iv_point);
        View pointView2 = rootView.findViewById(R.id.iv_point2);

        final int xTrans = DisplayUtils.dp2px(40);
        final int yTrans = DisplayUtils.dp2px(100);

        mAnimatorSet = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(pointView, "translationX", -xTrans, xTrans);
//        translationX.setRepeatCount(ValueAnimator.INFINITE);
        translationX.setInterpolator(new CircleInterpolator());

        ObjectAnimator translationY = ObjectAnimator.ofFloat(pointView2, "translationY", -yTrans, yTrans);
//        translationY.setRepeatCount(ValueAnimator.INFINITE);
        translationY.setInterpolator(new CircleInterpolator());

        mAnimatorSet.playTogether(translationX, translationY);
        mAnimatorSet.setDuration(3000);
        mAnimatorSet.setStartDelay(200);
        mAnimatorSet.start();

//        pointView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake1);
//                animation.setInterpolator(new CycleInterpolator(3));
//                pointView.startAnimation(animation);
//            }
//        }, 1000);

        rootView.findViewById(R.id.btn_action_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAnimatorSet.isRunning()) {
                    mAnimatorSet.start();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        mLoadingView.stop();
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
        }
        super.onDestroyView();
    }

    public static class CircleInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            return 0;
        }
    }
}
