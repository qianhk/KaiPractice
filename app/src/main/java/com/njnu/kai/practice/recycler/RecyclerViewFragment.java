package com.njnu.kai.practice.recycler;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.recycler.modal.Category;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.base.ActionBarLayout;

import java.util.ArrayList;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author kai
 * @since 17/1/8
 */
public class RecyclerViewFragment extends BaseTestFragment {

    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;

    private int mSeed;

    @Override
    protected void onInitActionBar() {
        super.onInitActionBar();
        addImageAction(0, R.drawable.ic_launcher);
    }

    @Override
    protected void onActionClick(int actionId, ActionBarLayout.Action action) {
        super.onActionClick(actionId, action);
        ++mSeed;
        mAdapter.appendData(makeACategory("from Action " + mSeed, mSeed));
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_recyclerview, viewGroup, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mAdapter = new MultiTypeAdapter(makeDataList());
        mAdapter.applyGlobalMultiTypePool();
        mAdapter.register(Text.class, new TextItemViewProvider());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public static ArrayList<Object> makeDataList() {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.addAll(makeACategory("First Category", 5));
        dataList.addAll(makeACategory("Second Category", 3));
        return dataList;
    }

    public static ArrayList<Object> makeACategory(String title, int subCount) {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.add(new Category(title));
        for (int idx = 1; idx <= subCount; ++idx) {
            dataList.add(new Text(title.hashCode() + "_" + idx));
        }
        return dataList;
    }
}
