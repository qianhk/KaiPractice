package com.njnu.kai.practice.image;

import android.widget.ImageView;
import com.njnu.kai.support.image.ImageLoader;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-5-9
 */
public class OneNormalImageTestFragment extends BaseImageTestFragment {

    @Override
    protected void onInitActionBar() {
        super.onInitActionBar();
        setTitle("one normal loader");
    }

    @Override
    protected void loadImageForView(ImageView view, int pos) {
        super.loadImageForView(view, pos);
        if (pos == 0) {
            ImageLoader.loadImage(view, TEST_IMAGE_URL, ImageLoader.RATIO1, 0);
        }
    }
}
