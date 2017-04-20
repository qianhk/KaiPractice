package com.njnu.kai.practice.entry;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import com.njnu.kai.practice.R;

/**
 * Created on 2015/1/15 10:11.
 *
 * @author ran.huang
 * @version 1.0.0
 */
public class EntryAnimatorActivity extends Activity {
    private LightingLayout mLightingLayout;
    private LogoView mLogoImage;
    private TextView mTextView01;
    private TextView mTextView02;
    private TextView mTextView03;
    private TextView mTextView04;
    private AnimatorSet mAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_animator);
        initViews();
    }

    private void initViews() {
        mLightingLayout = (LightingLayout) findViewById(R.id.entry_lighting_layout);
        mLogoImage = (LogoView) findViewById(R.id.imageview_logo);
        mTextView01 = (TextView)findViewById(R.id.entry_first_text);
        mTextView02 = (TextView)findViewById(R.id.entry_second_text);
        mTextView03 = (TextView)findViewById(R.id.entry_third_text);
        mTextView04 = (TextView)findViewById(R.id.entry_fourth_text);
        /**TODO need delete start*/
        mLogoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatorSet.cancel();
//                mLogoImage.invalidate();
                startEntryAnimation();
            }
        });
        /**TODO need delete end*/
        startEntryAnimation();
    }

    /**
     * 执行启动界面动画事件
     */
    private void startEntryAnimation() {
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(playBackLightingAnimation(), playTextAnimation(), playLogoStartAnimation());
        mAnimatorSet.playSequentially(playTextAnimation(), playLogoEndAnimation());
        mAnimatorSet.start();
    }

    private Animator playBackLightingAnimation() {
        return mLightingLayout.getAnimator();
    }

    private Animator playLogoStartAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        int logoSize = (int)getResources().getDimension(R.dimen.image_size_entry_logo);
//        ViewCompat.setPivotX(mLogoImage, logoSize / 2);
//        ViewCompat.setPivotY(mLogoImage, logoSize / 2);
        mLogoImage.setPivotX(logoSize / 2);
        mLogoImage.setPivotY(logoSize / 2);
        ObjectAnimator animX = ObjectAnimator.ofFloat(mLogoImage, "ScaleX", 0.5f, 1f).setDuration(1200);
        ObjectAnimator animY = ObjectAnimator.ofFloat(mLogoImage, "ScaleY", 0.5f, 1f).setDuration(1200);
        animatorSet.playTogether(animX, animY);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        return animatorSet;
    }

    private Animator playLogoEndAnimation() {
        return mLogoImage.getOutspreadAnimator();
    }

    private Animator playTextAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        final float startY = 100;
        final int duration = 800;
        ViewCompat.setAlpha(mTextView01, 0);
        ViewCompat.setAlpha(mTextView02, 0);
        ViewCompat.setAlpha(mTextView03, 0);
        ViewCompat.setAlpha(mTextView04, 0);
        ObjectAnimator animatorAlpha01 = ObjectAnimator.ofFloat(mTextView01, "alpha", 0, 1).setDuration(0);
        ObjectAnimator animator01 = ObjectAnimator.ofFloat(mTextView01, "translationY", startY, 0).setDuration(duration);
        ObjectAnimator animatorAlpha02 = ObjectAnimator.ofFloat(mTextView02, "alpha", 0, 1).setDuration(0);
        ObjectAnimator animator02 = ObjectAnimator.ofFloat(mTextView02, "translationY", startY, 0).setDuration(duration);
        ObjectAnimator animatorAlpha03 = ObjectAnimator.ofFloat(mTextView03, "alpha", 0, 1).setDuration(0);
        ObjectAnimator animator03 = ObjectAnimator.ofFloat(mTextView03, "translationY", startY, 0).setDuration(duration);
        ObjectAnimator animatorAlpha04 = ObjectAnimator.ofFloat(mTextView04, "alpha", 0, 1).setDuration(0);
        ObjectAnimator animator04 = ObjectAnimator.ofFloat(mTextView04, "translationY", startY, 0).setDuration(duration);
        animatorSet.playTogether(animator01, animatorAlpha01);
        animatorSet.playTogether(animator02, animatorAlpha02);
        animatorSet.playTogether(animator03, animatorAlpha03);
        animatorSet.playTogether(animator04, animatorAlpha04);
        animatorSet.setInterpolator(new DecelerateInterpolator(2.0f));
        animatorSet.playSequentially(animator01, animator02, animator03, animator04);
        return animatorSet;
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        });
    }

}

/*
测试图片压缩:

原始png:
ic_ptr_bkg.png 241kb
img_artist.png 894kb

打包后： 202.9kb 719.8kb


压缩的png： 69kb  259kb




 */