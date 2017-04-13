package com.njnu.kai.practice.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/13
 */
public class ConstraintLayoutFragment extends BaseTestFragment {
    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_constraint_layout, viewGroup, false);
    }
}
