package com.njnu.kai.practice.animator;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.ProxyActivity;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-9-1
 */
public class FrameAnimationActivity extends ProxyActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.normal_background));
        ImageView imageView = new ImageView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addContentView(imageView, params);
        Drawable drawable = getResources().getDrawable(R.drawable.img_duck_anim);
        imageView.setBackgroundDrawable(drawable);
        ((AnimationDrawable)drawable).start();
    }
}