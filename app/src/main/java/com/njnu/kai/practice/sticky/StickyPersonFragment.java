package com.njnu.kai.practice.sticky;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.SDKVersionUtils;

import java.util.ArrayList;


/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/18
 */
public class StickyPersonFragment extends BaseTestFragment {

    private StickPersonAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private String mYearString = "";
    private TextView mTvTitleLayout;
    private int mLayoutPadding = DisplayUtils.dp2px(10);
    private LinearLayoutManager mLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = layoutInflater.getContext();
        FrameLayout layout = new FrameLayout(context);
        mRecyclerView = new RecyclerView(context);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StickPersonAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setBackgroundColor(0x20000000);
        mRecyclerView.addItemDecoration(new LeftSpacesItemDecoration(mLayoutPadding));
        ViewGroup.MarginLayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = DisplayUtils.dp2px(20);
        layout.addView(mRecyclerView, layoutParams);

        mTvTitleLayout = new TextView(context);
        mTvTitleLayout.setTextColor(0xFFFF3333);
        mTvTitleLayout.setBackgroundColor(0xFFB5B5B5);
        if (SDKVersionUtils.hasLollipop()) {
            mTvTitleLayout.setLetterSpacing(0.1f);
        }
        mTvTitleLayout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        ViewGroup.MarginLayoutParams titleLayoutParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(37), DisplayUtils.dp2px(17));
//        titleLayoutParams.topMargin = DisplayUtils.dp2px(20);
        layout.addView(mTvTitleLayout, titleLayoutParams);

        return layout;
    }

    private void recyclerViewOnScrolled(RecyclerView recyclerView, int dx) {
        int pos = 1;
        int findFirstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        int findFirstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        View view = recyclerView.findViewHolderForAdapterPosition(findFirstVisibleItemPosition).itemView;
        View view2 = recyclerView.findViewHolderForAdapterPosition(findFirstCompletelyVisibleItemPosition).itemView;
        if (dx <= 0) {
            if (view2.getLeft() < mLayoutPadding) {
                pos = 0;
            }
            int i3 = findFirstVisibleItemPosition;
            findFirstVisibleItemPosition = pos;
            pos = i3;
        } else if (findFirstVisibleItemPosition == 0) {
            if (view.getLeft() > mLayoutPadding) {
                pos = 0;
            }
            findFirstVisibleItemPosition = pos;
            pos = findFirstCompletelyVisibleItemPosition;
        } else {
            if (view2.getLeft() > mLayoutPadding) {
                pos = 0;
            }
            findFirstVisibleItemPosition = pos;
            pos = findFirstCompletelyVisibleItemPosition;
        }
        if (findFirstVisibleItemPosition != 0) {
            if (!mYearString.equals(mAdapter.yearString(pos))) {
                mTvTitleLayout.setText(mAdapter.yearString(pos));
                mYearString = mAdapter.yearString(pos);
            }
            if (mTvTitleLayout.getTranslationX() != 0.0f) {
                mTvTitleLayout.setTranslationX(0.0f);
            }
            if (mTvTitleLayout.getVisibility() != View.VISIBLE) {
                mTvTitleLayout.setVisibility(View.VISIBLE);
            }
        }
        if (mAdapter.showYearView(findFirstCompletelyVisibleItemPosition)) {
            pos = mTvTitleLayout.getMeasuredWidth();
            if (view2.getLeft() > pos || view2.getLeft() < mLayoutPadding) {
                mTvTitleLayout.setTranslationX(0.0f);
            } else {
                mTvTitleLayout.setTranslationX((float) (view2.getLeft() - pos));
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                recyclerViewOnScrolled(recyclerView, dx);
            }
        });
        prepareData();
    }

    private void prepareData() {
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person("1999", "第一个人", R.drawable.p1_1));
        personList.add(new Person("2003", "第二个人", R.drawable.p1_2));
        personList.add(new Person("2008", "第三个人", R.drawable.p1_3));
        personList.add(new Person("2008", "第四个人", R.drawable.p2));
        personList.add(new Person("2011", "第五个人", R.drawable.p2_1));
        personList.add(new Person("2011", "第六个人", R.drawable.p2_2));
        personList.add(new Person("2011", "第七个人", R.drawable.p2_3));
        personList.add(new Person("2014", "第八个人", R.drawable.p3));
        personList.add(new Person("2015", "第九个人", R.drawable.p4));
        personList.add(new Person("2016", "第十个人", R.drawable.p5));
        personList.add(new Person("2016", "第11个人", R.drawable.p6));
        personList.add(new Person("2017", "第12个人", R.drawable.p7));
        personList.add(new Person("2017", "第13个人", R.drawable.p1));
        mAdapter.updateData(personList);
    }
}
