package com.njnu.kai.practice.scroll;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.njnu.kai.support.LogUtils;

/**
 * Created by kai
 * since 16/10/28
 */
public class ManageTwoScrollView extends LinearLayout implements NestedScrollingParent {

    private static final String TAG = "ManageTwoScrollView";

    private NestedScrollingParentHelper mParentHelper;

    public ManageTwoScrollView(Context context) {
        super(context);
        init(context);
    }

    public ManageTwoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ManageTwoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mParentHelper = new NestedScrollingParentHelper(this);
    }


    // NestedScrollingParent

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true; //(nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
//        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
//        stopNestedScroll();
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
//        final int oldScrollY = getScrollY();
//        scrollBy(0, dyUnconsumed);
//        final int myConsumed = getScrollY() - oldScrollY;
//        final int myUnconsumed = dyUnconsumed - myConsumed;
//        dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null);
    }

    private boolean addHeight;

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        dispatchNestedPreScroll(dx, dy, consumed, null);

        //处理子view传上来的事件
        //头部高度
        int headerHeight = getChildAt(0).getHeight();

        LogUtils.i(TAG, "lookScroll onNestedPreScroll dy=%d hH=%d top=%d tY=%.2f tSy=%d"
                , dy, headerHeight, getTop(), target.getY(), target.getScrollY());
        if (dy > 0) {
            //向上滑动
            if (Math.abs(this.getTop() - dy) <= headerHeight) {
                //header 在向上滑动的过程
                this.layout(this.getLeft(), this.getTop() - dy, this.getRight(), this.getBottom() - dy);
//                if (!addHeight) {
//                    //只增加一次 高度 height
//                    addHeight = true;
//                    ViewGroup.LayoutParams params = this.getLayoutParams();
//                    params.height = headerHeight + this.getHeight();
//                    this.setLayoutParams(params);
//                    requestLayout();
//                }
                consumed[1] += dy;
            } else {
                //当用户滑动动作太大，一次位移太大就会把parent view滑动脱离底部屏幕
                if ((this.getTop() + headerHeight) > 0) {
                    int offsetY = headerHeight + this.getTop();
                    this.layout(this.getLeft(), this.getTop() - offsetY, this.getRight(), this.getBottom() - offsetY);
                    consumed[1] += offsetY;
                }
            }
        }
        if (dy < 0) {
            //向下滑动
            if (getTop() < 0) {
                if (target.getScrollY() <= 0) {
                    if (Math.abs(getTop()) <= headerHeight) {
                        this.layout(this.getLeft(), this.getTop() + Math.abs(dy), this.getRight(), this.getBottom() + Math.abs(dy));
                        consumed[1] += dy;
                    }
//                if ((this.getTop() + Math.abs(dy)) <= 0) {
//                    //header在向下滑动的过程
//                    //this.getTop是负数dy也是负数所以需要+dy的绝对值
//                    this.layout(this.getLeft(), this.getTop() + Math.abs(dy), this.getRight(), this.getBottom() + Math.abs(dy));
//                    consumed[1] += dy;
//                } else {
//                    if (this.getTop() < 0) {
//                        int offsetY = Math.abs(this.getTop());
//                        this.layout(this.getLeft(), this.getTop() + offsetY, this.getRight(), this.getBottom() + offsetY);
//                        consumed[1] += offsetY;
//                    }
//                }
                }
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (!consumed) {
//            flingWithNestedDispatch((int) velocityY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false; //dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }


}
