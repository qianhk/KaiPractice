package com.njnu.kai.practice.animator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.njnu.kai.practice.R;

/**
 * Created by kai
 * since 16/11/25
 */
public class LoadingView extends FrameLayout {

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

        ImageView wholeView = new ImageView(context);
        wholeView.setImageResource(R.drawable.ic_lo_header);
        wholeView.setScaleType(ImageView.ScaleType.CENTER);
        addView(wholeView);

        ImageView mouthView = new ImageView(context);
        mouthView.setImageResource(R.drawable.ic_lo_zui);
        mouthView.setScaleType(ImageView.ScaleType.CENTER);
        addView(mouthView);

        ImageView organView = new ImageView(context);
        organView.setImageResource(R.drawable.ic_lo_heart);
        organView.setScaleType(ImageView.ScaleType.CENTER);
        addView(organView);

    }

    public void start() {

    }

    public boolean isStart() {
        return false;
    }

    public void stop() {

    }
}
