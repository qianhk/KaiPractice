package com.njnu.kai.practice.entry;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import com.njnu.kai.support.DisplayUtils;

/**
 * Created on 2015/1/15 10:52.
 *
 * @author ran.huang
 * @version 1.0.0
 */
public class LightingLayout extends RelativeLayout implements ValueAnimator.AnimatorUpdateListener {

    private ShapeHolder mTopOvalHolder;
    private ShapeHolder mBottomOvalHolder;
    private final static int RADIAL_GRADIENT_COLOR = 0xff003F87;
    private final static int BACKGROUND_COLOR = 0Xff000000;
    private final static int DURATION_RADIAL_GRADIENT = 1000;
    private ValueAnimator mAnimator;

    public LightingLayout(Context context) {
        super(context);
        init();
    }

    public LightingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LightingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LightingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }



    /**
     * 控件初始化
     */
    private void init() {
        setBackgroundColor(Color.BLACK);
        createTopOvalShape();
        createBottomOvalShape();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void createTopOvalShape() {
        float radius = DisplayUtils.getWidthPixels();
        float x = - radius / 2;
        float y = - radius * 3 /2;
        RadialGradient radialGradient = new RadialGradient(radius
                , radius, radius, RADIAL_GRADIENT_COLOR, BACKGROUND_COLOR, Shader.TileMode.CLAMP);
        mTopOvalHolder = createOvalShape(radius, x, y, radialGradient);
    }

    private void createBottomOvalShape() {
        float radius = DisplayUtils.getWidthPixels();
        float x = - radius / 2;
        float y = radius;
        RadialGradient radialGradient = new RadialGradient(radius
                , radius, radius, RADIAL_GRADIENT_COLOR, BACKGROUND_COLOR, Shader.TileMode.CLAMP);
        mBottomOvalHolder = createOvalShape(radius, x, y, radialGradient);
    }
    private ShapeHolder createOvalShape(float radius,float x, float y, RadialGradient radialGradient) {
        OvalShape topShape = new OvalShape();
        topShape.resize(radius * 2, radius * 2);
        ShapeDrawable shapeDrawable = new ShapeDrawable(topShape);
        ShapeHolder shapeHolder = new ShapeHolder(shapeDrawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        Paint paint = shapeDrawable.getPaint();
        paint.setShader(radialGradient);
        return shapeHolder;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mTopOvalHolder.getX(), mTopOvalHolder.getY());
        mTopOvalHolder.getShapeDrawable().draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.translate(mBottomOvalHolder.getX(), mBottomOvalHolder.getY());
        mBottomOvalHolder.getShapeDrawable().draw(canvas);
        canvas.restore();
        super.onDraw(canvas);
    }

    /**
     * 启动背景动画
     */
    public void startAnimation() {
        createAnimation();
        mAnimator.start();
    }

    public ValueAnimator getAnimator() {
        createAnimation();
        return mAnimator;
    }

    private void createAnimation() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofInt(BACKGROUND_COLOR, RADIAL_GRADIENT_COLOR);
            mAnimator.setEvaluator(new ArgbEvaluator());
            mAnimator.setDuration(DURATION_RADIAL_GRADIENT);
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.addUpdateListener(this);
            mAnimator.start();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float radius = DisplayUtils.getWidthPixels();
        RadialGradient radialGradient = new RadialGradient(radius
                , radius, radius, (Integer)animation.getAnimatedValue(), BACKGROUND_COLOR, Shader.TileMode.CLAMP);
        mTopOvalHolder.getShapeDrawable().getPaint().setShader(radialGradient);
        mBottomOvalHolder.getShapeDrawable().getPaint().setShader(radialGradient);
        invalidate();
    }

}
