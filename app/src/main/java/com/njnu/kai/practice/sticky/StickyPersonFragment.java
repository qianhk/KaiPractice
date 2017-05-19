package com.njnu.kai.practice.sticky;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.DisplayUtils;

import java.util.ArrayList;


/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/18
 */
public class StickyPersonFragment extends BaseTestFragment {

    private StickPersonAdapter mAdapter;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LinearLayout linearLayout = new LinearLayout(layoutInflater.getContext());
        RecyclerView recyclerView = new RecyclerView(layoutInflater.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new StickPersonAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setBackgroundColor(0x20000000);
        recyclerView.addItemDecoration(new LeftSpacesItemDecoration(DisplayUtils.dp2px(10)));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = DisplayUtils.dp2px(20);
        linearLayout.addView(recyclerView, layoutParams);
        return linearLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
