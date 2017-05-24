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
import com.njnu.kai.support.LogUtils;
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
    private TextView mTvTitle;
    private int mLayoutPadding = DisplayUtils.dp2px(12);
    private LinearLayoutManager mLayoutManager;

    private FrameLayout mTitleLayout;

    private int mHeaderBeginPos = 0;

    private RecyclerView.AdapterDataObserver mAdapterDataObserver;

    private RecyclerView mRecyclerView2;
    private LinearLayoutManager mLayoutManager2;
    private StickPersonAdapter mAdapter2;
    private StickyHeaderView mStickyHeaderView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = layoutInflater.getContext();
        FrameLayout layout = new FrameLayout(context);
        mRecyclerView = new RecyclerView(context);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StickPersonAdapter(false);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setBackgroundColor(0x20000000);
        mRecyclerView.addItemDecoration(new LeftSpacesItemDecoration(mLayoutPadding));
        ViewGroup.MarginLayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = DisplayUtils.dp2px(0);
        layout.addView(mRecyclerView, layoutParams);

        mTitleLayout = new FrameLayout(context);
        mTitleLayout.setBackgroundColor(Color.WHITE);
        ViewGroup.MarginLayoutParams titleLayoutParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(37) + mLayoutPadding, DisplayUtils.dp2px(17));
        titleLayoutParams.topMargin = DisplayUtils.dp2px(0);
        layout.addView(mTitleLayout, titleLayoutParams);

        final int floatColor = Color.RED; //0xFFB5B5B5;

        View titleLineView = new View(context);
        titleLineView.setBackgroundColor(floatColor);
        FrameLayout.LayoutParams titleLineLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.half_dp));
        titleLineLayoutParams.gravity = Gravity.CENTER;
        mTitleLayout.addView(titleLineView, titleLineLayoutParams);

        mTvTitle = new TextView(context);
        mTvTitle.setTextColor(floatColor);
        mTvTitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg_sticky_time_line_year));
        if (SDKVersionUtils.hasLollipop()) {
//            mTvTitle.setLetterSpacing(0.1f);
        }
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mTvTitle.setGravity(Gravity.CENTER);
        ViewGroup.MarginLayoutParams tvTitleParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(37), DisplayUtils.dp2px(17));
        tvTitleParams.leftMargin = mLayoutPadding;
        mTitleLayout.addView(mTvTitle, tvTitleParams);

        View view = new View(context);
        ViewGroup.MarginLayoutParams viewLayoutParams = new FrameLayout.LayoutParams(mLayoutPadding, DisplayUtils.dp2px(4));
        view.setBackgroundColor(Color.CYAN);
        layout.addView(view, viewLayoutParams);

        Button button1 = new Button(context);
        button1.setText("左拉70dp");
        ViewGroup.MarginLayoutParams button1LayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button1LayoutParams.topMargin = DisplayUtils.dp2px(220);
        layout.addView(button1, button1LayoutParams);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerViewOnScrolled(mRecyclerView, 10);
                mRecyclerView2.scrollBy(-DisplayUtils.dp2px(70), 0);
            }
        });
//        button1.setVisibility(View.GONE);

        Button button2 = new Button(context);
        button2.setText("左拉30dp");
        ViewGroup.MarginLayoutParams button2LayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button2LayoutParams.topMargin = DisplayUtils.dp2px(220);
        button2LayoutParams.leftMargin = DisplayUtils.dp2px(90);
        layout.addView(button2, button2LayoutParams);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerViewOnScrolled(mRecyclerView, -10);
                mRecyclerView2.scrollBy(-DisplayUtils.dp2px(30), 0);
            }
        });
//        button2.setVisibility(View.GONE);

        Button button3 = new Button(context);
        button3.setText("左拉3dp");
        ViewGroup.MarginLayoutParams button3LayoutParams3 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button3LayoutParams3.topMargin = DisplayUtils.dp2px(220);
        button3LayoutParams3.leftMargin = DisplayUtils.dp2px(180);
        layout.addView(button3, button3LayoutParams3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerViewOnScrolled(mRecyclerView, -10);
                mRecyclerView2.scrollBy(-DisplayUtils.dp2px(3), 0);
            }
        });


        Button button = new Button(context);
        button.setText("右拉70dp");
        ViewGroup.MarginLayoutParams buttonLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.topMargin = DisplayUtils.dp2px(260);
        layout.addView(button, buttonLayoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerViewOnScrolled(mRecyclerView, 10);
                mRecyclerView2.scrollBy(DisplayUtils.dp2px(70), 0);
            }
        });
//        button.setVisibility(View.GONE);

        button = new Button(context);
        button.setText("右拉30dp");
        buttonLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.topMargin = DisplayUtils.dp2px(260);
        buttonLayoutParams.leftMargin = DisplayUtils.dp2px(90);
        layout.addView(button, buttonLayoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerViewOnScrolled(mRecyclerView, -10);
                mRecyclerView2.scrollBy(DisplayUtils.dp2px(30), 0);
            }
        });
//        button.setVisibility(View.GONE);

        button = new Button(context);
        button.setText("右拉3dp");
        buttonLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.topMargin = DisplayUtils.dp2px(260);
        buttonLayoutParams.leftMargin = DisplayUtils.dp2px(180);
        layout.addView(button, buttonLayoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recyclerViewOnScrolled(mRecyclerView, -10);
                mRecyclerView2.scrollBy(DisplayUtils.dp2px(3), 0);
            }
        });

        button = new Button(context);
        button.setText("Update");
        buttonLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.topMargin = DisplayUtils.dp2px(240);
        buttonLayoutParams.leftMargin = DisplayUtils.dp2px(270);
        layout.addView(button, buttonLayoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareData();
            }
        });

        mAdapterDataObserver = new ClassAdapterDataObserver();
        mAdapter.registerAdapterDataObserver(mAdapterDataObserver);


        mRecyclerView2 = new RecyclerView(context);
        mLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mAdapter2 = new StickPersonAdapter(true);
        mRecyclerView2.setAdapter(mAdapter2);
//        mRecyclerView.setBackgroundColor(0x20000000);
        mRecyclerView2.addItemDecoration(new LeftSpacesItemDecoration(mLayoutPadding));
        ViewGroup.MarginLayoutParams layoutParams2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.topMargin = DisplayUtils.dp2px(320);
        layout.addView(mRecyclerView2, layoutParams2);

        mStickyHeaderView = new StickyHeaderView(context);
        ViewGroup.MarginLayoutParams stickyLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        stickyLayoutParams.topMargin = DisplayUtils.dp2px(320);
        layout.addView(mStickyHeaderView, stickyLayoutParams);

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
                mTvTitle.setText(mAdapter.yearString(newTitlePos));
                mYearString = mAdapter.yearString(newTitlePos);
            }
            mTitleLayout.setTranslationX(mHeaderBeginPos);
            setViewVisibility(mTitleLayout, View.VISIBLE);
        }

        if (completelyVisiblePosition == 0) {
            if (completeVisibleViewLeft >= mLayoutPadding) {
                setViewVisibility(mTitleLayout, View.INVISIBLE);
            }
        }
        if (visiblePosition == 0 && visibleView.getLeft() < mLayoutPadding) {
            setViewVisibility(mTitleLayout, View.VISIBLE);
        }

        if (mAdapter.showYearView(completelyVisiblePosition)) {
            int titleLayoutWidth = mTitleLayout.getMeasuredWidth();
            if (completeVisibleViewLeft > titleLayoutWidth || completeVisibleViewLeft < mLayoutPadding) {
                mTitleLayout.setTranslationX(mHeaderBeginPos);
            } else {
                mTitleLayout.setTranslationX(completeVisibleViewLeft - titleLayoutWidth);
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
                mTvTitle.setText(mAdapter.yearString(0));
                mYearString = mAdapter.yearString(0);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LogUtils.e(TAG, "onScrolled dx=%d", dx);
                recyclerViewOnScrolled(recyclerView, dx);
            }
        });
        mRecyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LogUtils.e(TAG, "onScrolled dx=%d", dx);
                mStickyHeaderView.translationWhole(dx);
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
        personList.add(new Person("2000", "第一个人", R.drawable.p1_1));
        personList.add(new Person("2001", "第二个人", R.drawable.p1_2));
        personList.add(new Person("2002", "第三个人", R.drawable.p1_3));
        personList.add(new Person("2002", "第四个人", R.drawable.p2));
        personList.add(new Person("2003", "第五个人", R.drawable.p2_1));
        personList.add(new Person("2003", "第六个人", R.drawable.p2_2));
        personList.add(new Person("2003", "第七个人", R.drawable.p2_3));
        personList.add(new Person("2004", "第八个人", R.drawable.p3));
        personList.add(new Person("2005", "第九个人", R.drawable.p4));
        personList.add(new Person("2006", "第十个人", R.drawable.p5));
        personList.add(new Person("2006", "第11个人", R.drawable.p6));
        personList.add(new Person("2007", "第12个人", R.drawable.p7));
        personList.add(new Person("2007", "第13个人", R.drawable.p1));
        mAdapter.updateData(personList);
        mAdapter2.updateData(personList);
        mRecyclerView2.scrollToPosition(0);

        ArrayList<StickyHeaderView.Info> infoList = new ArrayList<>();
        String year = null;
        final int singleViewWidth = DisplayUtils.dp2px(118);
        int viewWidth = 0;
        for (int idx = 0; idx < personList.size(); ++idx) {
            Person person = personList.get(idx);
            if (person.mYear.equals(year)) {
                viewWidth += singleViewWidth;
            } else {
                infoList.add(new StickyHeaderView.Info(person.mYear, viewWidth, singleViewWidth));
                year = person.mYear;
                viewWidth = singleViewWidth;
            }
        }
        mStickyHeaderView.updateData(mLayoutPadding, infoList);
    }
}
