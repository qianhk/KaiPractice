package com.njnu.kai.practice.animator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

/**
 * Created by kai
 * since 16/11/25
 */
public class LoadingAnimatorFragment extends BaseTestFragment {

    private LoadingView mLoadingView;
    private Button mBtnActionLoading;

    private void flushLoadingActionText() {
        mBtnActionLoading.setText(mLoadingView.isStart() ? "停止" : "开始");
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_loading_animator, viewGroup, false);
        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading_view);
        mBtnActionLoading = (Button) rootView.findViewById(R.id.btn_action_loading);
        mLoadingView.start();
        flushLoadingActionText();
        mBtnActionLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadingView.isStart()) {
                    mLoadingView.stop();
                } else {
                    mLoadingView.start();
                }
                flushLoadingActionText();
            }
        });
        return rootView;
    }


}
