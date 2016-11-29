package com.njnu.kai.practice.animator;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by kai
 * since 16/11/28
 */
public class TestPtrFrameLayout extends PtrFrameLayout {

    private TestPtrHeader mTestPtrHeader;

    public TestPtrFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public TestPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mTestPtrHeader = new TestPtrHeader(context);
        setHeaderView(mTestPtrHeader);
        addPtrUIHandler(mTestPtrHeader);
    }
}
