package com.njnu.kai.practice.entry;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;

/**
 * Created on 2015/1/15 10:16.
 *
 * @author ran.huang
 * @version 1.0.0
 */
public class ShapeHolder {

    private float mX = 0;
    private float mY = 0;
    private ShapeDrawable mShapeDrawable;
    private RadialGradient mRadialGradient;
    private float mAlpha;
    private int mColor;
    private Paint mPaint;

    /**
     * 画笔
     * @return
     */
    public Paint getPaint() {
        return mPaint;
    }

    /**
     * 画笔
     * @param paint Paint
     */
    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    /**
     * Constructor
     * @param shapeDrawable ShapeDrawable
     */
    public ShapeHolder(ShapeDrawable shapeDrawable) {
        mShapeDrawable = shapeDrawable;
    }

    /**
     * @return 水平方向位置
     */
    public float getX() {
        return mX;
    }

    /**
     * @param x 水平方向位置
     */
    public void setX(float x) {
        mX = x;
    }

    /**
     * @return 垂直方向位置
     */
    public float getY() {
        return mY;
    }

    /**
     *
     * @param y 垂直方向位置
     */
    public void setY(float y) {
        mY = y;
    }

    /**
     *
     * @return ShapeDrawable
     */
    public ShapeDrawable getShapeDrawable() {
        return mShapeDrawable;
    }

    /**
     *
     * @param shapeDrawable ShapeDrawable
     */
    public void setShapeDrawable(ShapeDrawable shapeDrawable) {
        mShapeDrawable = shapeDrawable;
    }

    /**
     *
     * @return RadialGradient
     */
    public RadialGradient getRadialGradient() {
        return mRadialGradient;
    }

    /**
     *
     * @param radialGradient RadialGradient
     */
    public void setRadialGradient(RadialGradient radialGradient) {
        mRadialGradient = radialGradient;
    }

    /**
     * @return 透明度
     */
    public float getAlpha() {
        return mAlpha;
    }

    /**
     * 设置透明度
     * @param alpha float
     */
    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    /**
     * 获取颜色
     * @return
     */
    public int getColor() {
        return mColor;
    }

    /**
     * 设置颜色
     * @param color int
     */
    public void setColor(int color) {
        mShapeDrawable.getPaint().setColor(color);
        mColor = color;
    }

    /**
     * 获取Shape宽度
     * @return
     */
    public float getWidth() {
        return mShapeDrawable.getShape().getWidth();
    }

    /**
     * 获取Shape高度
     * @return
     */
    public float getHeight() {
        return mShapeDrawable.getShape().getHeight();
    }
}
