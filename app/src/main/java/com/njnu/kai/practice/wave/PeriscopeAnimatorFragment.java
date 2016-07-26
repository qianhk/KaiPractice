package com.njnu.kai.practice.wave;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/7/24
 */
public class PeriscopeAnimatorFragment extends BaseTestFragment implements View.OnClickListener {

    protected PeriscopeLayout rlDisplay;
    protected View viewClick;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_periscope_animator, container, false);

        Resources resources = inflater.getContext().getResources();
        Drawable[] drawables = new Drawable[3];
        drawables[0] = resources.getDrawable(R.drawable.pl_blue);
        drawables[1] = resources.getDrawable(R.drawable.pl_red);
        drawables[2] = resources.getDrawable(R.drawable.pl_yellow);

        rlDisplay = (PeriscopeLayout) view.findViewById(R.id.rl_display);
        rlDisplay.setDrawables(drawables);

        viewClick = (View) view.findViewById(R.id.view_click);
        viewClick.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        rlDisplay.addHeart();
    }
}
