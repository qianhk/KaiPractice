package com.njnu.kai.practice.scroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

/**
 * Created by kai
 * since 16/10/28
 */
public class PullRefreshFragment extends BaseTestFragment {

    private PullRefreshView mPrfView;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_pull_refresh_view, viewGroup, false);
        mPrfView = (PullRefreshView) view.findViewById(R.id.prv_view);
        View headerView = layoutInflater.inflate(R.layout.test_drag_update_layout, null);
        mPrfView.addHeaderView(headerView);
        return view;
    }
}
