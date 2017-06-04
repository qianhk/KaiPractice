package com.njnu.kai.practice.tag;

import android.view.View;

import java.util.List;

/**
 * from https://github.com/cshzhang/tagflowlayout-lib
 */
public abstract class TagAdapter<T> {
    private List<T> mTagList;
    private OnDataChangeListener mOnDataChangeListener;

    public TagAdapter(List<T> tagList) {
        mTagList = tagList;
    }

    public void flushView(List<T> tagList) {
        mTagList = tagList;
        notifyDataChanged();
    }

    interface OnDataChangeListener {
        void onChanged();
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        mOnDataChangeListener = listener;
    }

    public int getCount() {
        return mTagList == null ? 0 : mTagList.size();
    }

    public T getItem(int position) {
        return mTagList.get(position);
    }

    public void notifyDataChanged() {
        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onChanged();
        }
    }

    public abstract View getView(FlowLayout parent, int position, T t);
}
