package com.njnu.kai.practice.sticky;

import android.content.Context;
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
        public String mTitle;
        public int mWidth;
        public int mViewWidth;

        protected int mWholeTx;
        protected int mTx;

        public Info(String title, int width, int viewWidth) {
            mTitle = title;
            mWidth = width;
            mViewWidth = viewWidth;
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
//            if (childView.getVisibility() == View.VISIBLE) {
            mViewPool.add((TextView) childView);
            ((ViewGroup) childView.getParent()).removeView(childView);
//                childView.setVisibility(View.INVISIBLE);
//            }
        }
        if (mInfoList == null || mInfoList.isEmpty()) {
            return;
        }
        int x = mLeftPadding;
        for (Info info : infoList) {
            TextView textView = getTextView();
            textView.setText(info.mTitle);
            x += info.mWidth;
            info.mWholeTx = x;
            info.mTx = x;
        }
        updateViewTranslationX();
    }

    private void updateViewTranslationX() {
        int childCount = getChildCount();
        for (int idx = 0; idx < childCount; ++idx) {
            View childView = getChildAt(idx);
            childView.setTranslationX(mInfoList.get(idx).mTx);
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

    private StringBuilder mBuilder = new StringBuilder();

    private void makeBeforeUseAfterTx(Info before, Info after, int viewWidth) {
        before.mTx = after.mTx - viewWidth;
    }

    public void translationWhole(int dx, int completelyVisiblePosition) {
        int childCount = getChildCount();
//        for (int idx = 0; idx < childCount; ++idx) {
//            View childView = getChildAt(idx);
//            childView.setTranslationX(childView.getTranslationX() - dx);
//        }

        for (Info info : mInfoList) {
            info.mWholeTx -= dx;
            info.mTx -= dx;
        }

        mBuilder.setLength(0);
        mBuilder.append("lookTranslation: dx=" + dx + " ");

        if (dx > 0) {
            for (int idx = 0; idx < childCount; ++idx) {
                int titleViewWidth = getChildAt(idx).getMeasuredWidth();
                Info info = mInfoList.get(idx);
                int jdx = idx + 1;
//                mBuilder.append(String.format(Locale.getDefault(), " idx=%d tx=%d vH=%d tx+vh=%d", idx, info.mTx, titleViewWidth, info.mTx + titleViewWidth));
                if (info.mTx < 0) {
                    if (info.mTx + titleViewWidth > 0) {
                        if (jdx < childCount) {
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
                        Info before = mInfoList.get(idx - 1);
                        if (info.mTx > mLeftPadding + titleViewWidth) {
                            before.mTx = mLeftPadding;
                        } else {
                            before.mTx = info.mTx - titleViewWidth;
                        }
                    } else {
                        if (jdx < childCount) {
                            Info after = mInfoList.get(jdx);
                            int intervalX = after.mTx - info.mTx;
//                            mBuilder.append(String.format(Locale.getDefault(), " A j_tx=%d interval=%d", after.mTx, intervalX));
                            if (intervalX > titleViewWidth) {
                                info.mTx = mLeftPadding;
                            } else {
                                info.mTx = after.mTx - titleViewWidth;
                            }
//                            mBuilder.append(String.format(Locale.getDefault(), " A tx amend = %d", info.mTx));

                        } else {
                            if (info.mTx < mLeftPadding) {
                                info.mTx = mLeftPadding;
//                                mBuilder.append(String.format(Locale.getDefault(), " A else tx amend = %d", info.mTx));
                            }
                        }
                    }
                    break;
                }
            }
        } else if (dx < 0) {
            for (Info info : mInfoList) {
                info.mTx = info.mWholeTx;
            }
        }

        LogUtils.d(TAG, mBuilder.toString());

//
//        Info before = null, after = null;
//        View beforeView = null;
//        if (dx > 0) {
//            for (int idx = 0; idx < childCount; ++idx) {
//                Info info = mInfoList.get(idx);
//                if (info.mWholeTx < 0) {
//
//                } else if (info.mWholeTx <= mLeftPadding) {
//                    info.mTx = mLeftPadding;
//                    break;
//                } else {
//                    after = info;
//                    int intervalX = after.mWholeTx - before.mWholeTx;
//                    before.mTx = mLeftPadding;
//                    intervalX = after.mWholeTx - before.mTx;
//                    int titleViewWidth = beforeView.getMeasuredWidth();
//                    if (intervalX < titleViewWidth) {
//                        before.mTx = after.mWholeTx - titleViewWidth;
//                    }
//                    break;
//                }
//                before = info;
//                beforeView = getChildAt(idx);
//            }
//        }

//        if (dx > 0) { //内容逐渐展示右边的
//            for (int idx = 0; idx < childCount; ++idx) {
//                View childView = getChildAt(idx);
//                Info info = mInfoList.get(idx);
//                float translationX = info.mTx;
//                if (translationX >= 0f && translationX <= mLeftPadding) {
//                    info.mTx = mLeftPadding;
//                    int nextIdx = idx + 1;
//                    if (nextIdx < childCount) {
//                        Info nextInfo = mInfoList.get(nextIdx);
//                        int viewWidth = childView.getMeasuredWidth();
//                        float intervalX = nextInfo.mTx - info.mTx;
//                        if (intervalX < viewWidth) {
//                            info.mTx = nextInfo.mTx - viewWidth;
//                        }
//                    }
//                }
//            }
//        } else if (dx < 0) { //内容逐渐展示左边的
//
//        }
        updateViewTranslationX();

    }
}
