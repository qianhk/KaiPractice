package com.njnu.kai.practice.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.njnu.kai.practice.R;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-11-24
 */
public class SplashActivity extends Activity {

    private View mRootView;

    private Animation loadZoomoutAnimatin() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_zoom_out);
        return animation;
    }

    private void doZoomoutAnimation() {
        mRootView.setOnClickListener(null);
        Animation animation = loadZoomoutAnimatin();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRootView.startAnimation(animation);
    }

    private void animationEnd() {
        mRootView.setOnClickListener(mOnClickListener);
        finish();
        startActivity(new Intent(SplashActivity.this, BehideSplashActivity.class));
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            finish();
            int id = v.getId();
            if (id == R.id.btn_method1) {
                doZoomoutAnimation();
            } else {
                startActivity(new Intent(SplashActivity.this, BehideSplashActivity.class));
    //            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                overridePendingTransition(R.anim.splash_zoom_in, R.anim.splash_zoom_out);

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.main_splash, null);
        setContentView(mRootView);
        findViewById(R.id.btn_method1).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_method2).setOnClickListener(mOnClickListener);
        int i = 0;
        int j = 100 / i;
    }
}
