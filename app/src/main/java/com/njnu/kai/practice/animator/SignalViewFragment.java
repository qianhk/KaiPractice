package com.njnu.kai.practice.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.LogUtils;

import net.vidageek.mirror.dsl.AccessorsController;
import net.vidageek.mirror.dsl.Mirror;

import java.util.concurrent.TimeUnit;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/3/29
 */
public class SignalViewFragment extends BaseTestFragment implements View.OnClickListener {

    private static final String TAG = "SignalViewFragment";

    private ObjectAnimator mObjectAnimator;
    private ObjectAnimator mObjectAnimator2;
    private RotateDrawable mDrawable;
    private RotateDrawable mDrawable2;
    private int mLevel = 0;

    public static final int DURATION = 2000;
    private TestFrameCallback mFrameCallback;
    private TextView mResultView;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_signal, viewGroup, false);
        mResultView = (TextView) rootView.findViewById(R.id.tv_result);
        View drawableView = rootView.findViewById(R.id.view_drawable);
        mDrawable = (RotateDrawable) layoutInflater.getContext().getResources().getDrawable(R.drawable.xml_footer_refresh);
        drawableView.setBackgroundDrawable(mDrawable);
//        rootView.findViewById(R.id.btn_add_500).setOnClickListener(this);
        mObjectAnimator = ObjectAnimator.ofInt(mDrawable, "level", 0, 10000);
        mObjectAnimator.setDuration(DURATION);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        View drawableView2 = rootView.findViewById(R.id.view_drawable2);
        mDrawable2 = (RotateDrawable) layoutInflater.getContext().getResources().getDrawable(R.drawable.xml_footer_refresh);
        drawableView2.setBackgroundDrawable(mDrawable2);
//        rootView.findViewById(R.id.btn_add_500).setOnClickListener(mOnClickListener);
        mObjectAnimator2 = ObjectAnimator.ofInt(mDrawable2, "level", 0, 10000);
        mObjectAnimator2.setDuration(DURATION);
        mObjectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator2.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float) ((int) ((input * 360) / 30) * 30) / 360;
            }
        });

        rootView.findViewById(R.id.btn_frame_callback).setOnClickListener(this);
        rootView.findViewById(R.id.btn_frame_delay).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mObjectAnimator.start();
        mObjectAnimator2.start();

        AccessorsController controller = new Mirror().on(Choreographer.getInstance());
        Object sFrameDelay = controller.get().field("sFrameDelay");
        Object USE_VSYNC = controller.get().field("USE_VSYNC");
        Object USE_FRAME_TIME = controller.get().field("USE_FRAME_TIME");

        String info = "sFrameDelay: " + sFrameDelay + "    USE_VSYNC: " + USE_VSYNC + "   USE_FRAME_TIME: " + USE_FRAME_TIME;
        mResultView.setText(info);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mObjectAnimator.cancel();
        mObjectAnimator2.cancel();
    }

    private static long mBeginUptime;

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_add_500) {
            mLevel += 500;
            if (mLevel > 10000) {
                mLevel = 0;
            }
            mDrawable.setLevel(mLevel);
        } else if (viewId == R.id.btn_frame_callback) {
            if (mFrameCallback == null) {
                mFrameCallback = new TestFrameCallback();
            }
            Choreographer instance = Choreographer.getInstance();
            mBeginUptime = SystemClock.uptimeMillis();
            LogUtils.d(TAG, "lookFrame postFrameCallback uptimeMs=%d", mBeginUptime);
            instance.postFrameCallback(mFrameCallback);
        } else if (viewId == R.id.btn_frame_delay) {
            AccessorsController controller = new Mirror().on(Choreographer.getInstance());
            controller.set().field("sFrameDelay").withValue(100L);
            Long sFrameDelay = (Long) controller.get().field("sFrameDelay");
            LogUtils.d(TAG, "lookFrame modify  sFrameDelay value=%s", sFrameDelay);
        }
    }

    private static class TestFrameCallback implements Choreographer.FrameCallback {

        @Override
        public void doFrame(long frameTimeNanos) {
            long endUptime = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos);
            LogUtils.d(TAG, "lookFrame FrameCallback doFrame msTime=%d interval=%d", endUptime, endUptime - mBeginUptime);
        }
    }
}

/*
find . -name 'properties.h'

#define PROP_PATH_RAMDISK_DEFAULT  "/default.prop"
#define PROP_PATH_SYSTEM_BUILD     "/system/build.prop"
#define PROP_PATH_VENDOR_BUILD     "/vendor/build.prop"
#define PROP_PATH_LOCAL_OVERRIDE   "/data/local.prop"
#define PROP_PATH_FACTORY          "/factory/factory.prop"

*/