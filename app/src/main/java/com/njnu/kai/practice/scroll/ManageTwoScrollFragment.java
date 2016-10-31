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
public class ManageTwoScrollFragment extends BaseTestFragment {

    private ManageTwoScrollView mPrfView;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_manage_two_scroll_view, viewGroup, false);
        mPrfView = (ManageTwoScrollView) view.findViewById(R.id.prv_view);
        return view;
    }
}
