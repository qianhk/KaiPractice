package com.njnu.kai.practice;

import android.animation.*;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.*;
import android.widget.TextView;
import com.njnu.kai.support.ProxyActivity;
import com.njnu.kai.support.DisplayUtils;

import java.util.Random;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-9-1
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PropertyAnimationActivity extends ProxyActivity {

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Random random = new Random();
            v.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tween);

        View view = findViewById(R.id.v_alpha);
        ValueAnimator fadeInAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        fadeInAnimator.setDuration(1500);
        fadeInAnimator.setRepeatCount(ValueAnimator.INFINITE);
        fadeInAnimator.setRepeatMode(ValueAnimator.REVERSE);
        fadeInAnimator.start();

        view = findViewById(R.id.v_translate);
        ValueAnimator translateAnimator = ObjectAnimator.ofFloat(view, "x", 20.0f, DisplayUtils.getWidthPixels() - 100);
        translateAnimator.setDuration(2000);
        translateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        translateAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        translateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimator.start();
        view.setOnClickListener(mOnClickListener);

        view = findViewById(R.id.v_rotate);
        ValueAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        rotateAnimator.setDuration(1000);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        translateAnimator.setInterpolator(new LinearInterpolator());
//        translateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimator.start();

        view = findViewById(R.id.v_rotate_big);
        AnimatorSet animator = (AnimatorSet)AnimatorInflater.loadAnimator(this, R.anim.unlimited_rotate_big_animator);
        animator.setTarget(view);
        animator.start();

        view = findViewById(R.id.v_scale);
        ValueAnimator scaleAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 0.3f, 1f);
        scaleAnimatorX.setDuration(1800);
        scaleAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
        ValueAnimator scaleAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 0.3f, 1f);
        scaleAnimatorY.setDuration(1800);
        scaleAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleAnimatorX, scaleAnimatorY);
        animatorSet.start();

        TextView textView = (TextView)findViewById(R.id.v_scale_text);
        textView.setTextSize(24);
        textView.setText("Property Animation Demo");
    }
}