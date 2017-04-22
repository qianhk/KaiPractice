package com.njnu.kai.practice.recycler.modal;

import me.drakeet.multitype.BaseVO;

/**
 * @author kai
 * @since 17/1/8
 */
public class Text extends BaseVO {

    public String mText;

    public boolean mClickable;

    public Text(String text) {
        mText = text;
    }

    public Text(String text, boolean clickable) {
        mText = text;
        mClickable = clickable;
    }
}
