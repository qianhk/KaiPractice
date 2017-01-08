package com.njnu.kai.practice.recycler;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.recycler.modal.Category;
import com.njnu.kai.support.BaseTestFragment;

import java.util.ArrayList;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author kai
 * @since 17/1/8
 */
public class RecyclerViewFragment extends BaseTestFragment {

    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_recyclerview, viewGroup, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mAdapter = new MultiTypeAdapter(makeDataList());
        mAdapter.applyGlobalMultiTypePool();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public static ArrayList<Object> makeDataList() {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.addAll(makeACategory("First Category"));
        dataList.addAll(makeACategory("Second Category"));
        return dataList;
    }

    public static ArrayList<Object> makeACategory(String title) {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.add(new Category(title));
        return dataList;
    }
}
