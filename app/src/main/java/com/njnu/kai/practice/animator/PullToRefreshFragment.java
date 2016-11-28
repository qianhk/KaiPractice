package com.njnu.kai.practice.animator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by kai
 * since 16/11/28
 */
public class PullToRefreshFragment extends BaseTestFragment {
    private View mRootView;
    private LinearLayout mLayoutBusinessRoot;
    private TestPtrFrameLayout mPtrLayout;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_pull_to_refresh, viewGroup, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mRootView = rootView;
        mLayoutBusinessRoot = (LinearLayout) rootView.findViewById(R.id.layout_business_root);
        mPtrLayout = (TestPtrFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mPtrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 3000);
            }
        });
    }
}
