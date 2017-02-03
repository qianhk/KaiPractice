package me.drakeet.multitype;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author kai
 * @since 17/2/3
 */
public class BaseViewHolder<DATA extends BaseVO> extends RecyclerView.ViewHolder {

    private OnMultiTypeViewListener mMultiTypeViewListener;
    protected DATA mData;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void bindView(DATA data) {
        mData = data;
        if (data.mViewHeight != 0) {
            itemView.getLayoutParams().height = data.mViewHeight;
        }
        if (data.mPaddingLeft >= 0) {
            itemView.setPadding(data.mPaddingLeft, data.mPaddingTop, data.mPaddingRight, data.mPaddingBottom);
        }

        if (data.mWholeBackgroundColor != 0) {
            itemView.setBackgroundColor(data.mWholeBackgroundColor);
        } else if (data.mTransparentWholeBkgColor) {
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void clearClickListener(View view) {
        view.setOnClickListener(null);
        view.setClickable(false);
    }

    void setMultiTypeViewListener(OnMultiTypeViewListener multiTypeViewListener) {
        mMultiTypeViewListener = multiTypeViewListener;
    }

    protected void notifyMultiTypeValueChanged() {
        if (mMultiTypeViewListener != null) {
            mMultiTypeViewListener.onMultiTypeValueChanged(mData, null);
        }
    }

    protected void notifyMultiTypeViewClicked(String action) {
        if (mMultiTypeViewListener != null) {
            mMultiTypeViewListener.onMultiTypeViewClicked(mData, action);
        }
    }
}
