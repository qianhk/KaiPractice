package com.njnu.kai.practice.sticky;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.njnu.kai.support.LogUtils;

public class LeftSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "LeftSpacesItemDecoration";
    private int mSpace;

    public LeftSpacesItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = 0;

        // Add top margin only for the first item to avoid double mSpace between items
        int childLayoutPosition = parent.getChildLayoutPosition(view);
//        LogUtils.d(TAG, "getItemOffsets child position=%d", childLayoutPosition);
        if (childLayoutPosition == 0) {
            outRect.left = mSpace;
        } else {
            outRect.left = 0;
        }
    }
}
