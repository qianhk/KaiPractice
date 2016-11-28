package com.njnu.kai.practice.animator;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by kai
 * since 16/11/28
 */
public class TestPtrHeader extends BasePtrHeader implements PtrUIHandler {

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        onUIReset();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        onUIRefreshPrepare();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        onUIRefreshBegin();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        onUIRefreshComplete();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        onUIPositionChange2(frame, isUnderTouch, status, ptrIndicator);
    }

    public TestPtrHeader(Context context) {
        super(context);
    }

    public TestPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
