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
import com.njnu.kai.support.LogUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/22
 */
public class StickyHeaderView extends FrameLayout {

    private static final String TAG = "StickyHeaderView";

    public static class Info {
        private String mTitle;
        private int mMarginLeft;
        private int mViewWidth;

        private int mWholeTx; //整体应处于的偏移值
        private int mTx; //当前应处于的经过修正后的偏移值

        public Info(String title, int marginLeft, int viewWidth) {
            mTitle = title;
            mMarginLeft = marginLeft;
            mViewWidth = viewWidth;
        }
    }

    public int mLeftPadding;
    public ArrayList<Info> mInfoList = new ArrayList<>();

    private View mLeftHolderView;

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
        int childCount = getChildCount();
        for (int idx = childCount - 1; idx >= 0; --idx) {
            View childView = getChildAt(idx);
            if (childView != mLeftHolderView) {
                mViewPool.add((TextView) childView);
            }
            ((ViewGroup) childView.getParent()).removeView(childView);
        }

        mInfoList.clear();
        int infoSize = infoList != null ? infoList.size() : 0;
        if (infoSize == 0) {
            return;
        }

        if (infoSize > 40) {
            mInfoList.addAll(infoList.subList(0, 40));
        } else {
            mInfoList.addAll(infoList);
        }

        int x = mLeftPadding;
        for (Info info : infoList) {
            TextView textView = getTextView();
            textView.setText(info.mTitle);
            x += info.mMarginLeft;
            info.mWholeTx = x;
            info.mTx = x;
        }
        if (mLeftHolderView == null) {
            mLeftHolderView = getLeftHolderView();
        }
        addView(mLeftHolderView);
        updateViewTranslationX();
    }

    private void updateViewTranslationX() {
        int infoSize = mInfoList.size();
        for (int idx = 0; idx < infoSize; ++idx) {
            View childView = getChildAt(idx);
            childView.setTranslationX(mInfoList.get(idx).mTx);
        }
        if (infoSize > 0) {
            Info info = mInfoList.get(0);
            mLeftHolderView.setTranslationX(info.mTx - mLeftHolderView.getMeasuredWidth());
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
//            textView.setVisibility(View.VISIBLE);
            addView(textView);
            return textView;
        }
    }

    private View getLeftHolderView() {
        View holderView = new View(getContext());
        holderView.setBackgroundColor(Color.WHITE);
        ViewGroup.MarginLayoutParams layoutParams = new FrameLayout.LayoutParams(mLeftPadding, DisplayUtils.dp2px(17));
        holderView.setLayoutParams(layoutParams);
        return holderView;
    }

    private StringBuilder mBuilder = new StringBuilder();

    public void translationWhole(int dx) {
        for (Info info : mInfoList) {
            info.mWholeTx -= dx;
            info.mTx -= dx;
        }

        mBuilder.setLength(0);
        mBuilder.append(String.format(Locale.getDefault(), "lookTranslation: dx=%d ", dx));

        if (dx > 0) {
            scrollToRight();
        } else if (dx < 0) {
            scrollToLeft();
        }

        int infoSize = mInfoList.size();
        boolean needLog = false;
        for (int idx = 1; idx < infoSize; ++idx) {
            Info beforeInfo = mInfoList.get(idx - 1);
            int beforeTx = beforeInfo.mTx;
            int viewWidth = getChildAt(idx - 1).getMeasuredWidth();
            Info info = mInfoList.get(idx);
            int tx = info.mTx;
            if (beforeTx + viewWidth > tx) {
                needLog = true;
                mBuilder.append(String.format(Locale.getDefault(), " calc: %s wTx=%d tx=%d, %s wTx=%d tx=%d cha=%d"
                        , beforeInfo.mTitle, beforeInfo.mWholeTx, beforeInfo.mTx
                        , info.mTitle, info.mWholeTx, info.mTx
                        , beforeTx + viewWidth - tx));
            }
        }
        if (needLog) {
            LogUtils.e(TAG, mBuilder.toString());
        }

        updateViewTranslationX();
    }

    private void scrollToLeft() {
        int titleCount = mInfoList.size();
        for (int idx = 0; idx < titleCount; ++idx) {
            int titleViewWidth = getChildAt(idx).getMeasuredWidth();
            Info info = mInfoList.get(idx);
            if (info.mTx >= mLeftPadding) {
                mBuilder.append(String.format(Locale.getDefault(), " info %s tx=%d wTx=%d", info.mTitle, info.mTx, info.mWholeTx));
                if (info.mWholeTx > 0) {
                    if (idx > 0) {
                        Info before = mInfoList.get(idx - 1);
                        mBuilder.append(String.format(Locale.getDefault(), " before wTx=%d", before.mWholeTx));
                        if (info.mWholeTx > mLeftPadding + titleViewWidth) {
                            before.mTx = mLeftPadding;
                        } else {
                            before.mTx = info.mWholeTx - titleViewWidth;
                            info.mTx = info.mWholeTx;
                        }
                    } else {
                        info.mTx = mLeftPadding;
                    }
                }
                if (info.mWholeTx < mLeftPadding) {
                    if (idx > 0) {
                        Info before = mInfoList.get(idx - 1);
                        if (before.mTx + titleViewWidth > mLeftPadding) {
                            info.mTx = before.mTx + titleViewWidth;
                        } else {
                            info.mTx = mLeftPadding;
                        }
                    } else {
                        info.mTx = mLeftPadding;
                    }
                }
                break;
            }
        }
    }

    private void scrollToRight() {
        int titleCount = mInfoList.size();
        for (int idx = 0; idx < titleCount; ++idx) {
            int titleViewWidth = getChildAt(idx).getMeasuredWidth();
            Info info = mInfoList.get(idx);
            int jdx = idx + 1;
            mBuilder.append(String.format(Locale.getDefault(), " idx=%d tx=%d vW=%d tx+vW=%d", idx, info.mTx, titleViewWidth, info.mTx + titleViewWidth));
            if (info.mTx < 0) {
                if (info.mTx + titleViewWidth > 0) {
                    if (jdx < titleCount) {
                        Info after = mInfoList.get(jdx);
                        mBuilder.append(String.format(Locale.getDefault(), " a j_tx=%d", after.mTx));
                        if (after.mTx > mLeftPadding + titleViewWidth) {
                            info.mTx = mLeftPadding;
                        } else {
                            if (after.mTx > mLeftPadding) {
                                info.mTx = after.mTx - titleViewWidth;
                            } else {
                                after.mTx = mLeftPadding;
                            }
                        }
                        break;
                    } else {
                        info.mTx = mLeftPadding;
                    }
                }
            } else {
                if (info.mTx > mLeftPadding) {
                    if (idx > 0) {
                        Info before = mInfoList.get(idx - 1);
                        if (info.mTx > mLeftPadding + titleViewWidth) {
                            before.mTx = mLeftPadding;
                        } else {
                            before.mTx = info.mTx - titleViewWidth;
                        }
                    }
                } else {
                    if (jdx < titleCount) {
                        Info after = mInfoList.get(jdx);
                        mBuilder.append(String.format(Locale.getDefault(), " A j_tx=%d tx+vW=%d", after.mTx, info.mTx + titleViewWidth));
                        if (after.mTx > mLeftPadding + titleViewWidth ) {
                            info.mTx = mLeftPadding;
                        } else {
                            info.mTx = after.mTx - titleViewWidth;
                        }
                        mBuilder.append(String.format(Locale.getDefault(), " A tx amend = %d", info.mTx));
                    } else {
                        if (info.mTx < mLeftPadding) {
                            info.mTx = mLeftPadding;
                            mBuilder.append(String.format(Locale.getDefault(), " A else tx amend = %d", info.mTx));
                        }
                    }
                }
                break;
            }
        }
    }
}
