package com.njnu.kai.practice.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.njnu.kai.practice.R;

/**
 */
public class LabelTagAdapter extends TagAdapter<Object> {

    public LabelTagAdapter() {
        super(null);
    }

    @Override
    public View getView(FlowLayout parent, int position, Object tag) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TextView tv = (TextView) inflater.inflate(R.layout.item_tag_label, parent, false);
        if (tag instanceof CharSequence) {
            tv.setText((CharSequence) tag);
        } else {
            tv.setText(tag.toString());
        }
        return tv;
    }

}
