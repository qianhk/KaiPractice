package com.njnu.kai.practice.image.shadow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

/**
 * @author kai
 * @since 17/4/3
 */
public class ShadowImageFragment extends BaseTestFragment {

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_shadow_image, viewGroup, false);
    }
}
