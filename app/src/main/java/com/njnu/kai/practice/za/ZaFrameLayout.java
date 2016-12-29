package com.njnu.kai.practice.za;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by kai
 * since 16/12/29
 */
public class ZaFrameLayout extends FrameLayout {

    public ZaFrameLayout(Context context) {
        super(context);
    }

    public ZaFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZaFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ZaFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isEnabled();
    }
}
