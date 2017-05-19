package com.njnu.kai.practice.sticky;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private static final String TAG = "StickyPersonFragment";
    private StickPersonAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private String mYearString = "";
    private TextView mTvTitleLayout;
    private int mLayoutPadding = DisplayUtils.dp2px(12);
    private LinearLayoutManager mLayoutManager;

    private int mHeaderBeginPos = 0;

    private RecyclerView.AdapterDataObserver mAdapterDataObserver;

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
        layoutParams.topMargin = DisplayUtils.dp2px(30);
        layout.addView(mRecyclerView, layoutParams);

        mTvTitleLayout = new TextView(context);
        mTvTitleLayout.setTextColor(0xFFFF3333);
        mTvTitleLayout.setBackgroundColor(0xFFB5B5B5);
        if (SDKVersionUtils.hasLollipop()) {
            mTvTitleLayout.setLetterSpacing(0.1f);
        }
        mTvTitleLayout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mTvTitleLayout.setGravity(Gravity.CENTER);
        ViewGroup.MarginLayoutParams titleLayoutParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(37) + mLayoutPadding, DisplayUtils.dp2px(17));
        titleLayoutParams.topMargin = DisplayUtils.dp2px(30);
        layout.addView(mTvTitleLayout, titleLayoutParams);
        mTvTitleLayout.setPadding(mLayoutPadding, 0, 0, 0);

        View view = new View(context);
        ViewGroup.MarginLayoutParams viewLayoutParams = new FrameLayout.LayoutParams(mLayoutPadding, DisplayUtils.dp2px(4));
        view.setBackgroundColor(Color.CYAN);
        layout.addView(view, viewLayoutParams);

        Button buttonLeft = new Button(context);
        buttonLeft.setText("向左拉了");
        ViewGroup.MarginLayoutParams buttonLeftLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLeftLayoutParams.topMargin = DisplayUtils.dp2px(250);
        layout.addView(buttonLeft, buttonLeftLayoutParams);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewOnScrolled(mRecyclerView, 10);
            }
        });

        Button buttonRight = new Button(context);
        buttonRight.setText("向右拉了");
        ViewGroup.MarginLayoutParams buttonRightLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonRightLayoutParams.topMargin = DisplayUtils.dp2px(250);
        buttonRightLayoutParams.leftMargin = DisplayUtils.dp2px(100);
        layout.addView(buttonRight, buttonRightLayoutParams);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewOnScrolled(mRecyclerView, -10);
            }
        });
        mAdapterDataObserver = new ClassAdapterDataObserver();
        mAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        return layout;
    }

    private void recyclerViewOnScrolled(RecyclerView recyclerView, int dx) {
        int newTitlePos;
        int visiblePosition = mLayoutManager.findFirstVisibleItemPosition();
        int completelyVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        boolean needChangeTitle;
        View visibleView = recyclerView.findViewHolderForAdapterPosition(visiblePosition).itemView;
        View completeVisibleView = recyclerView.findViewHolderForAdapterPosition(completelyVisiblePosition).itemView;
        int completeVisibleViewLeft = completeVisibleView.getLeft();
        if (dx <= 0) {
            if (completeVisibleViewLeft < mLayoutPadding) {
                needChangeTitle = false;
            } else {
                needChangeTitle = true;
            }
            newTitlePos = visiblePosition;
        } else {
            if (completeVisibleViewLeft > mLayoutPadding) {
                needChangeTitle = false;
            } else {
                needChangeTitle = true;
            }
            newTitlePos = completelyVisiblePosition;
        }

        if (needChangeTitle) {
            if (!mYearString.equals(mAdapter.yearString(newTitlePos))) {
                mTvTitleLayout.setText(mAdapter.yearString(newTitlePos));
                mYearString = mAdapter.yearString(newTitlePos);
            }
            mTvTitleLayout.setTranslationX(mHeaderBeginPos);
            setViewVisibility(mTvTitleLayout, View.VISIBLE);
        }

        if (completelyVisiblePosition == 0) {
            if (completeVisibleViewLeft >= mLayoutPadding) {
                setViewVisibility(mTvTitleLayout, View.INVISIBLE);
            }
        }
        if (visiblePosition == 0 && visibleView.getLeft() < mLayoutPadding) {
            setViewVisibility(mTvTitleLayout, View.VISIBLE);
        }

        if (mAdapter.showYearView(completelyVisiblePosition)) {
            int titleLayoutWidth = mTvTitleLayout.getMeasuredWidth();
            if (completeVisibleViewLeft > titleLayoutWidth || completeVisibleViewLeft < mLayoutPadding) {
                mTvTitleLayout.setTranslationX(mHeaderBeginPos);
            } else {
                mTvTitleLayout.setTranslationX(completeVisibleViewLeft - titleLayoutWidth);
            }
        }
    }

    private static void setViewVisibility(View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    private class ClassAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        public void onChanged() {
            if (mAdapter.getItemCount() > 0 && !mYearString.equals(mAdapter.yearString(0))) {
                mTvTitleLayout.setText(mAdapter.yearString(0));
                mYearString = mAdapter.yearString(0);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LogUtils.e(TAG, "onScrolled dx=%d", dx);
                recyclerViewOnScrolled(recyclerView, dx);
            }
        });
        prepareData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
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
