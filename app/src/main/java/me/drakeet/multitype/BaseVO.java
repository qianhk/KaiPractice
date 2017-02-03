package me.drakeet.multitype;

import java.io.Serializable;

/**
 * @author kai
 * @since 17/2/3
 */
public class BaseVO<RAW_DATA> implements Serializable {
    /**
     * > 0  具体高度px
     * == 0 布局里默认(不改变)
     * WRAP_CONTENT  自适应
     */
    public int mViewHeight;

    public int mWholeBackgroundColor;
    public boolean mTransparentWholeBkgColor;

    int mPaddingLeft = -1;
    int mPaddingRight = -1;
    int mPaddingTop = -1;
    int mPaddingBottom = -1;

    public RAW_DATA mRawData;

    public String mVoId;

    public void setWholePadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingRight = right;
        mPaddingTop = top;
        mPaddingBottom = bottom;
    }
}
