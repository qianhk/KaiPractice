package com.njnu.kai.practice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.njnu.kai.practice.guideanimator.GuideAnimatorShapeView;
import com.njnu.kai.practice.guideanimator.GuideAnimatorView;
import com.njnu.kai.support.ProxyActivity;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.SDKVersionUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-8-14
 */
public class AnimatorActivity extends ProxyActivity {

    public static final String KEY_FUNCTION = "key_function";

    private final static int TRANSLATE_VERTICAL_ANIMATION_STARTOFFSET = 500;
    private final static int TRANSLATE_VERTICAL_ANIMATION_DURATION = 500;
    private final static int TRANSLATE_HORIZONTAL_ANIMATION_STARTOFFSET = 1000;
    private final static int TRANSLATE_HORIZONTAL_ANIMATION_DURATION = 1500;
    private final static int SCALE_ANIMATION_DURATION = 300;

    private int mChickenWidth;
    private int mChickenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView background = new ImageView(this);
        background.setBackgroundColor(Color.WHITE);
        background.setImageResource(R.drawable.img_guide_background);
        background.setScaleType(ImageView.ScaleType.FIT_CENTER);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(background, layoutParams);
        Intent intent = getIntent();
        int function = intent.getIntExtra(KEY_FUNCTION, 0);
        if (function == 0) {
            if (SDKVersionUtils.hasHoneycomb()) {
                Button button = new Button(this);
                button.setText("Shape");
                FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonParams.topMargin = 100;
                buttonParams.leftMargin = 100;
                addContentView(button, buttonParams);
                button.setTag(1);
                button.setOnClickListener(mOnClickListener);

                button = new Button(this);
                button.setText("Chicken");
                buttonParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttonParams.topMargin = 200;
                buttonParams.leftMargin = 100;
                addContentView(button, buttonParams);
                button.setTag(2);
                button.setOnClickListener(mOnClickListener);

            }

            Button button = new Button(this);
            button.setText("View Animation");
            FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonParams.topMargin = 300;
            buttonParams.leftMargin = 100;
            addContentView(button, buttonParams);
            button.setTag(3);
            button.setOnClickListener(mOnClickListener);

        } else if (function == 1) {
            GuideAnimatorShapeView shapeView = new GuideAnimatorShapeView(this);
            addContentView(shapeView, layoutParams);
        } else if (function == 2) {
            final GuideAnimatorView animatorView = new GuideAnimatorView(this);
            animatorView.setDrawableResourceId(R.drawable.image_guide_duck_1, R.drawable.image_guide_duck_2, R.drawable.image_guide_duck_3);
            addContentView(animatorView, layoutParams);
            animatorView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animatorView.makeChickenAnimation(DisplayUtils.getWidthPixels() / 5 * 3, DisplayUtils.getHeightPixels() / 5);
                }
            }, 100);
        } else {
            BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(R.drawable.image_guide_duck_1);
            mChickenWidth = drawable.getIntrinsicWidth();
            mChickenHeight = drawable.getIntrinsicHeight();
            Bitmap bitmap = drawable.getBitmap();
            mChickenWidth = bitmap.getWidth();
            mChickenHeight = bitmap.getHeight();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Number tag = (Number)v.getTag();
            Intent intent = new Intent(AnimatorActivity.this, AnimatorActivity.class);
            intent.putExtra(KEY_FUNCTION, tag.intValue());
            startActivity(intent);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = getIntent();
        int function = intent.getIntExtra(KEY_FUNCTION, 0);
        if (function == 3) {
            final int rawX = (int)event.getRawX();
            final int rawY = (int)event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    doPrepareAnimationView(rawX, rawY);
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void doPrepareAnimationView(final int rawX, final int rawY) {
        final ImageView animationDuck = new ImageView(this);
        animationDuck.setImageResource(R.drawable.img_duck_anim);
        addContentView(animationDuck, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        animationDuck.layout(rawX - mChickenWidth / 2, rawY - 2 * mChickenHeight
                , rawX + mChickenWidth / 2, rawY - mChickenHeight);
        Animation scaleAnimation = getGuideDuckPressAnimation(DisplayUtils.getHeightPixels() - rawY + mChickenHeight / 2, animationDuck);
        animationDuck.startAnimation(scaleAnimation);
    }

    private Animation getGuideDuckPressAnimation(int top, final ImageView animationDuck) {
        AnimationSet animationSet = new AnimationSet(false);
        final float pivotValue = 0.5f;
        Animation scaleAnimation = new ScaleAnimation(0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
        scaleAnimation.setDuration(SCALE_ANIMATION_DURATION);
        animationSet.addAnimation(scaleAnimation);

        Animation translateAnimationVertical = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, top - mChickenHeight / 2);
        translateAnimationVertical.setStartOffset(TRANSLATE_VERTICAL_ANIMATION_STARTOFFSET);
        translateAnimationVertical.setDuration(TRANSLATE_VERTICAL_ANIMATION_DURATION);
        animationSet.addAnimation(translateAnimationVertical);

        final float toXValue = -1.2f;
        Animation translateAnimationHorizontal = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_PARENT, toXValue,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translateAnimationHorizontal.setStartOffset(TRANSLATE_HORIZONTAL_ANIMATION_STARTOFFSET);
        translateAnimationHorizontal.setDuration(TRANSLATE_HORIZONTAL_ANIMATION_DURATION);
        translateAnimationHorizontal.setInterpolator(new AnticipateInterpolator());
        animationSet.addAnimation(translateAnimationHorizontal);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                AnimationDrawable animationDrawable = (AnimationDrawable)animationDuck.getDrawable();
                animationDrawable.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationDrawable animationDrawable = (AnimationDrawable)animationDuck.getDrawable();
                animationDrawable.stop();
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup viewParent = (ViewGroup)animationDuck.getParent();
                        viewParent.removeView(animationDuck);
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        animationSet.setFillAfter(true);
        return animationSet;
    }
}
