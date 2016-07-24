package com.njnu.kai.practice.image;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.njnu.kai.support.DisplayUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-5-9
 */
public class GlideImageTestFragment extends BaseImageTestFragment {

    @Override
    protected void onInitActionBar() {
        super.onInitActionBar();
        setTitle("use Glide");
    }

    @Override
    protected void loadImageForView(ImageView view, int pos) {
        super.loadImageForView(view, pos);
//        Glide.with(this).load(TEST_IMAGE_URL).into(view);
        Glide.with(this).load(TEST_IMAGE_URL).override(DisplayUtils.getWidthPixels(), DisplayUtils.getWidthPixels()).into(view);
//        Glide.with(this).load(TEST_IMAGE_URL).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
    }
}
