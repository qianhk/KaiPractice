package com.njnu.kai.practice.recycler.modal;

import me.drakeet.multitype.BaseVO;

/**
 * @author kai
 * @since 17/2/3
 */
public class ButtonVO extends BaseVO {

    public int mViewWidth;

    public String mText;

    public String mUrlScheme;

    public int mTextSizeSp = 14;
    public int mTextColor;
    public int mBkgResId;
    public int mBackgroundColor;

    public int mGravity;

    public ButtonVO(String text, int textColor, int bkgResId) {
        mText = text;
        mTextColor = textColor;
        mBkgResId = bkgResId;
    }
}
