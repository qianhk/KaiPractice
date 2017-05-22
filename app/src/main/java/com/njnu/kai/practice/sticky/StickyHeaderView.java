package com.njnu.kai.practice.sticky;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.SDKVersionUtils;

import java.util.ArrayList;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/22
 */
public class StickyHeaderView extends FrameLayout {

    public static class Info {
        public String mTitle;
        public int mWidth;

        public Info(String title, int width) {
            mTitle = title;
            mWidth = width;
        }
    }


    public int mLeftPadding;
    public ArrayList<Info> mInfoList;

//    interface Adapter {
//        int leftPadding();
//        int viewWidth(int position);
//        int viewTitle(int position);
//    }

    private ArrayList<TextView> mViewPool = new ArrayList<>();

    public StickyHeaderView(@NonNull Context context) {
        super(context);
    }

    public StickyHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void updateData(int leftPadding, ArrayList<Info> infoList) {
        mLeftPadding = leftPadding;
        mInfoList = infoList;
        int childCount = getChildCount();
        for (int idx = 0; idx < childCount; ++idx) {
            View childView = getChildAt(idx);
            if (childView.getVisibility() == View.VISIBLE) {
                mViewPool.add((TextView) childView);
                childView.setVisibility(View.INVISIBLE);
            }
        }
        if (mInfoList == null || mInfoList.isEmpty()) {
            return;
        }
        int x = mLeftPadding;
        for (Info info : infoList) {
            TextView textView = getTextView();
            textView.setText(info.mTitle);
            x += info.mWidth;
            textView.setTranslationX(x);
        }
    }

    private TextView getTextView() {
        if (mViewPool.isEmpty()) {
            TextView tvTitle = new TextView(getContext());
            tvTitle.setTextColor(0xFFB5B5B5);
            tvTitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg_sticky_time_line_year));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvTitle.setGravity(Gravity.CENTER);
            ViewGroup.MarginLayoutParams tvTitleParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(37), DisplayUtils.dp2px(17));
            addView(tvTitle, tvTitleParams);
            return tvTitle;
        } else {
            TextView textView = mViewPool.remove(0);
            textView.setVisibility(View.VISIBLE);
            return textView;
        }
    }

    public void translationWhole(int dx) {
        int childCount = getChildCount();
        for (int idx = 0; idx < childCount; ++idx) {
            View childView = getChildAt(idx);
            childView.setTranslationX(childView.getTranslationX() - dx);
        }
    }
}
