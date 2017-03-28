package com.njnu.kai.practice.image;

import com.evernote.android.job.JobManager;
import com.njnu.kai.practice.job.DemoSyncJob;
import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-5-9
 */
public class ImageTestEntryFragment extends BaseTestListFragment {

    private int mJobId;

    @TestFunction("空view不加载")
    private void test00() {
        launchFragment(new BaseImageTestFragment());
    }

    @TestFunction("one glide加载")
    private void test10() {
        launchFragment(new OneGlideImageTestFragment());
    }

    @TestFunction("glide加载")
    private void test15() {
        launchFragment(new GlideImageTestFragment());
    }

    @TestFunction("one bitmap加载")
    private void test20() {
        launchFragment(new OneNormalImageTestFragment());
    }

    @TestFunction("bitmap加载")
    private void test25() {
        launchFragment(new NormalImageTestFragment());
    }

    @TestFunction("job test")
    private void test40() {
        mJobId = DemoSyncJob.scheduleJob();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JobManager.instance().cancel(mJobId);
    }
}
