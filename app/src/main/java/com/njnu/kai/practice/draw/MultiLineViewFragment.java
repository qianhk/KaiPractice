package com.njnu.kai.practice.draw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.support.BaseTestFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/10
 */
public class MultiLineViewFragment extends BaseTestFragment {
    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        MultiLineView multiLineView = new MultiLineView(layoutInflater.getContext());
        return multiLineView;
    }
}
