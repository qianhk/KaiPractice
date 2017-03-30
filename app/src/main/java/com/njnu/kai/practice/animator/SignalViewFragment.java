package com.njnu.kai.practice.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/3/29
 */
public class SignalViewFragment extends BaseTestFragment {

    private ObjectAnimator mObjectAnimator;
    private ObjectAnimator mObjectAnimator2;
    private RotateDrawable mDrawable;
    private RotateDrawable mDrawable2;
    private int mLevel = 0;

    public static final int DURATION = 2000;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.btn_add_500) {
                mLevel += 500;
                if (mLevel > 10000) {
                    mLevel = 0;
                }
                mDrawable.setLevel(mLevel);
            }
        }
    };

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View signalView = layoutInflater.inflate(R.layout.fragment_signal, viewGroup, false);

        View drawableView = signalView.findViewById(R.id.view_drawable);
        mDrawable = (RotateDrawable) layoutInflater.getContext().getResources().getDrawable(R.drawable.xml_footer_refresh);
        drawableView.setBackgroundDrawable(mDrawable);
//        signalView.findViewById(R.id.btn_add_500).setOnClickListener(mOnClickListener);
        mObjectAnimator = ObjectAnimator.ofInt(mDrawable, "level", 0, 10000);
        mObjectAnimator.setDuration(DURATION);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        View drawableView2 = signalView.findViewById(R.id.view_drawable2);
        mDrawable2 = (RotateDrawable) layoutInflater.getContext().getResources().getDrawable(R.drawable.xml_footer_refresh);
        drawableView2.setBackgroundDrawable(mDrawable2);
//        signalView.findViewById(R.id.btn_add_500).setOnClickListener(mOnClickListener);
        mObjectAnimator2 = ObjectAnimator.ofInt(mDrawable2, "level", 0, 10000);
        mObjectAnimator2.setDuration(DURATION);
        mObjectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator2.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float) ((int) ((input * 360) / 30) * 30) / 360;
            }
        });
        return signalView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mObjectAnimator.start();
        mObjectAnimator2.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mObjectAnimator.cancel();
        mObjectAnimator2.cancel();
    }
}
