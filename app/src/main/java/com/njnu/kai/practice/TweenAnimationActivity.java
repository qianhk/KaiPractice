package com.njnu.kai.practice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.njnu.kai.support.ProxyActivity;

import java.util.Random;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-9-1
 */
public class TweenAnimationActivity extends ProxyActivity {

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
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);

        view = findViewById(R.id.v_translate);
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_left_right));
        view.setOnClickListener(mOnClickListener);

        view = findViewById(R.id.v_rotate);
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.unlimited_rotate));

        view = findViewById(R.id.v_rotate_big);
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.unlimited_rotate_big));

        view = findViewById(R.id.v_scale);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.favorite_zoom_in_out_out);
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);

        view = findViewById(R.id.v_scale_text);
        animation = AnimationUtils.loadAnimation(this, R.anim.text_zoom_out);
        view.startAnimation(animation);
    }
}