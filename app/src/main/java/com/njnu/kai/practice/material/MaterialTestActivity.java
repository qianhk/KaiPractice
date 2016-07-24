package com.njnu.kai.practice.material;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.SDKVersionUtils;
import com.njnu.kai.support.ToastUtils;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MaterialTestActivity extends Activity implements View.OnClickListener {

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            int action = event.getAction();
            LogUtils.d("MaterialActivity", "onTouch0 action=%08X mask_action=%08X", action, event.getActionMasked());
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    LogUtils.d("MaterialActivity", "onTouch1 action=%08X mask_action=%08X", action, event.getActionMasked());
//                    view.setTranslationZ(120);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    LogUtils.d("MaterialActivity", "onTouch2 action=%08X mask_action=%08X", action, event.getActionMasked());
//                    view.setTranslationZ(0);
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        if (!SDKVersionUtils.hasLollipop()) {
            ToastUtils.showToast("仅支持Android5.0及以上系统");
            return;
        }
//        findViewById(R.id.iv_circle).setOnTouchListener(mOnTouchListener);
//        findViewById(R.id.iv_square).setOnTouchListener(mOnTouchListener);
        findViewById(R.id.iv_circle).setOnClickListener(this);
        findViewById(R.id.iv_square).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.iv_circle) {
            circularReveal(v, v.getWidth() / 2, v.getHeight() / 2, 0, (float)Math.hypot(v.getHeight() / 2, v.getWidth() / 2));
        } else if (viewId == R.id.iv_square) {
            circularReveal(v, v.getWidth() / 2, v.getHeight() / 2, 0, (float)Math.hypot(v.getHeight() / 2, v.getWidth() / 2));
        }
    }

    private void circularReveal(View view, int centerX,  int centerY, float startRadius, float endRadius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(
                view, centerX, centerY, startRadius, endRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(6000);
        animator.start();
    }
}
