package com.njnu.kai.practice.image.shadow;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.image.DrawableLess;
import com.njnu.kai.support.BaseTestFragment;

/**
 * @author kai
 * @since 17/4/3
 */
public class ShadowImageFragment extends BaseTestFragment {

    private View mRootView;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        mRootView = layoutInflater.inflate(R.layout.fragment_shadow_image, viewGroup, false);
        ImageView imageView = (ImageView) mRootView.findViewById(R.id.iv_test);
        imageView.setImageResource(R.drawable.heart);
        ImageView imageView2 = (ImageView) mRootView.findViewById(R.id.iv_test2);
        Drawable drawable = getResources().getDrawable(R.drawable.heart);
        imageView2.setImageDrawable(DrawableLess.$tint(drawable, Color.GREEN));
        return mRootView;
    }
}
