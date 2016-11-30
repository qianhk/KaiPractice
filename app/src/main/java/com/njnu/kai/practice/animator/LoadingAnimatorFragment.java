package com.njnu.kai.practice.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.LogUtils;

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
        View haTestView = rootView.findViewById(R.id.tv_ha_test);

        final int xTrans = DisplayUtils.dp2px(40);
        final int yTrans = DisplayUtils.dp2px(0);

        mAnimatorSet = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(haTestView, "translationX", -xTrans, xTrans);
//        translationX.setRepeatCount(ValueAnimator.INFINITE);
        translationX.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
//                LogUtils.e(TAG, "lookInterpolator translationX:" + input);
                return (float)(Math.sin(2  * Math.PI * input));
            }
        });

        ObjectAnimator translationY = ObjectAnimator.ofFloat(haTestView, "translationY", -yTrans, yTrans);
//        translationY.setRepeatCount(ValueAnimator.INFINITE);
        translationY.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
//                LogUtils.e(TAG, "lookInterpolator translationY:" + input);
//                return (float)(Math.sin(2  * Math.PI * input));
                return input;
            }
        });

        mAnimatorSet.playTogether(translationX, translationY);
        mAnimatorSet.setDuration(3000);
        mAnimatorSet.setStartDelay(200);
        mAnimatorSet.start();

//        haTestView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake1);
//                animation.setInterpolator(new CycleInterpolator(3));
//                haTestView.startAnimation(animation);
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
}
