package com.njnu.kai.practice.sticky;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        linearLayout.addView(recyclerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(100)));
        return linearLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Person> personList = new ArrayList<>();
        mAdapter.updateData(personList);

        Person person = new Person();
        person.mName = "第一个人";
        personList.add(person);

        person = new Person();
        person.mName = "第二个人";
        personList.add(person);

        person = new Person();
        person.mName = "第三个人";
        personList.add(person);

        person = new Person();
        person.mName = "第4个人";
        personList.add(person);

    }
}
