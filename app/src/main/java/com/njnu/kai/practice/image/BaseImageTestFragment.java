package com.njnu.kai.practice.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.base.ActionBarLayoutFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-5-9
 */
public class BaseImageTestFragment extends ActionBarLayoutFragment {

    private LinearLayout mLinearLayout;

    //    高清盆景 1920*1200
    protected static final String TEST_IMAGE_URL = "http://www.bz55.com/uploads/allimg/150305/139-1503051FS0.jpg";

    @Override
    protected void onInitActionBar() {
        super.onInitActionBar();
        setTitle("Empty Image view");
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_loader_test, container, false);
        mLinearLayout = (LinearLayout)view.findViewById(R.id.ll_parent);
        createSomeImageView();
        return view;
    }

    private void createSomeImageView() {
        for (int idx = 0; idx < 10; ++idx) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(0xFFDDDDDD);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(150));
            layoutParams.topMargin = DisplayUtils.dp2px(4);
            layoutParams.bottomMargin = layoutParams.topMargin;
            layoutParams.leftMargin = DisplayUtils.dp2px(idx);
            layoutParams.rightMargin = layoutParams.leftMargin;
            loadImageForView(imageView, idx);
            mLinearLayout.addView(imageView, layoutParams);
        }
    }

    protected void loadImageForView(ImageView view, int pos) {

    }
}
