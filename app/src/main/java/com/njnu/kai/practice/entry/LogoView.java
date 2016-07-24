package com.njnu.kai.practice.entry;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.njnu.kai.practice.R;

/**
 * Created on 2015/1/20 17:12.
 *
 * @author ran.huang
 * @version 1.0.0
 */
public class LogoView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static float MAX_MIDDLE_ICON_TRANSLATE_Y = 0.0f;
    private float mXferModeRadius;
    private final static float XFERMODE_OFFSET = 10.0f;
    private final static int OUT_SPREAD_DURATION = 1000;
    private float mRadius;
    private float mMiddleTranslateY;
    private float mUserIconTranslateY;
    private Paint mModePaint;
    private Bitmap mWhiteCircleBitmap;
    private Bitmap mBlueCircleBitmap;
    private Bitmap mLogoBitmap;
    private Matrix mMatrix = new Matrix();

    public float getUserIconTranslateY() {
        return mUserIconTranslateY;
    }

    public void setUserIconTranslateY(float userIconTranslateY) {
        mUserIconTranslateY = userIconTranslateY;
    }

    public float getMiddleTranslateY() {
        return mMiddleTranslateY;
    }

    public void setMiddleTranslateY(float middleTranslateY) {
        mMiddleTranslateY = middleTranslateY;
    }

    public LogoView(Context context) {
        super(context);
        init();
    }

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LogoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        initData();
        initModePaint();
        initBitmap();
    }

    private void initData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mRadius = getResources().getDimension(R.dimen.image_size_entry_logo) / 2.0f;
        MAX_MIDDLE_ICON_TRANSLATE_Y = mRadius * 2;
        mXferModeRadius = 0;
        mMiddleTranslateY = 0;
    }

    private void initModePaint() {
        mModePaint = new Paint();
        mModePaint.setAntiAlias(true);
        mModePaint.setFilterBitmap(true);
        mModePaint.setDither(true);
    }

    private void initBitmap() {
        mWhiteCircleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_white_circle);
        mBlueCircleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_blue_circle);
        mLogoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_mid_image);
    }

    public float getXferModeRadius() {
        return mXferModeRadius;
    }

    public void setXferModeRadius(float xferModeRadius) {
        mXferModeRadius = xferModeRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /**draw out circle*/
        final int imageWidth = mWhiteCircleBitmap.getWidth();
        final int imageHeight = mWhiteCircleBitmap.getHeight();
        mMatrix.setScale(getWidth() / (float) imageWidth, getHeight() / (float) imageHeight);
        canvas.drawBitmap(mWhiteCircleBitmap, mMatrix, null);
        /**draw big circle xfermode paint*/
        mModePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mXferModeRadius, mModePaint);
        mModePaint.setXfermode(null);

        /**draw in circle*/
        canvas.drawBitmap(mBlueCircleBitmap, mMatrix, null);

        /**draw small circle xfermode paint*/
        mModePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawCircle(getWidth() / 2.0f, getWidth() / 2.0f, mXferModeRadius, mModePaint);
        mModePaint.setXfermode(null);

        /**draw logo middle image*/
        mMatrix.postTranslate(0, getMiddleTranslateY());
        canvas.drawBitmap(mLogoBitmap, mMatrix, null);
    }


    /**
     * 局部展开动画
     */
    private Animator createOutspreadAnimation() {
        /**change xfermode paint radius*/
        AnimatorSet animator = new AnimatorSet();
        ValueAnimator inAnimator = ObjectAnimator.ofFloat(this, "xferModeRadius", mRadius / 3, mRadius + XFERMODE_OFFSET);
        inAnimator.setDuration(OUT_SPREAD_DURATION);
        inAnimator.addUpdateListener(this);
        ValueAnimator middleAnimator = ObjectAnimator.ofFloat(this, "middleTranslateY", 0, MAX_MIDDLE_ICON_TRANSLATE_Y);
        middleAnimator.setDuration(OUT_SPREAD_DURATION);
        middleAnimator.addUpdateListener(this);
        /** setting animatorSetdin
         * animator default Interpolator is AccelerateDecelerateInterpolator()
         * */
        animator.playTogether(inAnimator, middleAnimator);
        return animator;
    }

    /**
     * @return 局部展开动画
     */
    public Animator getOutspreadAnimator() {
        return createOutspreadAnimation();
    }

    @Override

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        /**刷新界面*/
        invalidate();
    }
}
