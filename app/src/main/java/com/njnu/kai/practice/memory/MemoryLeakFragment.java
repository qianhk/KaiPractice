package com.njnu.kai.practice.memory;


import android.view.View;
import android.widget.ImageView;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/3/17
 */
public class MemoryLeakFragment extends BaseTestListFragment {

    @TestFunction("创建1000个ImageView")
    public void onTest01() {
        for (int i = 0; i < 1000; i++) {
            View view = new ImageView(getActivity());
        }
    }
}
