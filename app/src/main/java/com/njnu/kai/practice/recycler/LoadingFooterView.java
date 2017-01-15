package com.njnu.kai.practice.recycler;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.RotateDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.njnu.kai.practice.R;

/**
 */
public class LoadingFooterView extends FrameLayout {

    protected State mState = State.Normal;

    private TextView mTextView;
    private RotateDrawable mDrawable;
    private ObjectAnimator mObjectAnimator;

    public LoadingFooterView(Context context) {
        this(context, null);
    }

    public LoadingFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public LoadingFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initViews(context);
    }

    public TextView getTextView() {
        return mTextView;
    }

    private void initViews(Context context) {
        mTextView = new TextView(context);
        mTextView.setPadding(0, (int) getResources().getDimension(R.dimen.footer_padding)
                , 0, (int) getResources().getDimension(R.dimen.footer_padding));
        final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mTextView, layoutParams);
        setTextColor(getResources().getColor(R.color.normal_subtitle));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mDrawable = (RotateDrawable) context.getResources().getDrawable(R.drawable.xml_footer_refresh);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setVisibility(GONE);

        final int duration = 1000;
        final int maxDegree = 10000;
        mObjectAnimator = ObjectAnimator.ofInt(mDrawable, "level", 0, maxDegree);
        mObjectAnimator.setDuration(duration);
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
    }

    public void showLoadingAnim() {
        mTextView.setVisibility(VISIBLE);
        setText(R.string.loading);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, mDrawable, null, null);
        mObjectAnimator.start();
    }

    public void showLastPageText(CharSequence text) {
        mTextView.setVisibility(VISIBLE);
        clearRefreshDrawable();
        setText(text);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    public void showLoadFailText() {
        mTextView.setVisibility(VISIBLE);
        clearRefreshDrawable();
        setText(R.string.load_next_page_failed);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    public void showLoadFailText(int stringResourceId) {
        mTextView.setVisibility(VISIBLE);
        clearRefreshDrawable();
        setText(stringResourceId);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    public void hide() {
        mTextView.setVisibility(GONE);
        clearRefreshDrawable();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearRefreshDrawable();
    }

    private void clearRefreshDrawable() {
        mObjectAnimator.cancel();
    }

    /**
     * @param color color
     */
    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

    /**
     * @param colors colors
     */
    public void setTextColor(ColorStateList colors) {
        mTextView.setTextColor(colors);
    }

    /**
     * @param size size
     */
    public void setTextSize(float size) {
        mTextView.setTextSize(size);
    }

    /**
     * @param unit unit
     * @param size size
     */
    public void setTextSize(int unit, float size) {
        mTextView.setTextSize(unit, size);
    }

    /**
     * @param text text
     */
    public final void setText(CharSequence text) {
        mTextView.setText(text);
    }

    /**
     * @param resid text resource id
     */
    public final void setText(int resid) {
        mTextView.setText(resid);
    }

    public static enum State {
        Normal/**正常*/
        , TheEnd/**加载到最底了*/
        , Loading/**加载中..*/
        , NetWorkError/**网络异常*/
    }

    public void setState(State state) {
        mState = state;
        if (state != State.Loading) {
            clearRefreshDrawable();
        }
        switch (state) {
            case Loading:
                showLoadingAnim();
                break;
            case TheEnd:
                hide();
                break;
            case NetWorkError:
                showLoadFailText();
                break;
            case Normal:
                hide();
                break;
        }
    }

    public State getState() {
        return mState;
    }
}