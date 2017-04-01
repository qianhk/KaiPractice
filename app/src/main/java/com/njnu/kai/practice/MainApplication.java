package com.njnu.kai.practice;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import com.evernote.android.job.JobManager;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.njnu.kai.practice.aidl.AidlTestFragment;
import com.njnu.kai.practice.aidl.AidlTestService;
import com.njnu.kai.practice.job.DemoJobCreator;
import com.njnu.kai.practice.recycler.MultiTypeInstaller;
import com.njnu.kai.support.AppRuntime;
import com.njnu.kai.support.BaseApplication;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.image.ImageLoader;

import java.io.File;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-4-29
 */
public class MainApplication extends BaseApplication {

    public static final boolean TEST_CONTINUE_CRASH = false;
    private TestLookActivityLifecycle mLookActivityLifecycle;

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(AppRuntime.Storage.getCachePath(this) + File.separator + "imageTest");
//        Stetho.initializeWithDefaults(this);
        AndroidThreeTen.init(this);
        MultiTypeInstaller.start();

        JobManager.create(this).addJobCreator(new DemoJobCreator());

        if (TEST_CONTINUE_CRASH) {
            Intent intent = new Intent(getApplicationContext(), AidlTestService.class);
            startService(intent);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int a = 20 / 0; //单纯的在application里制造crash基本不能造成连续crash,一般crash2次后停止
                }
            }, 100);
        }
        mLookActivityLifecycle = new TestLookActivityLifecycle();
        registerActivityLifecycleCallbacks(mLookActivityLifecycle);
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(mLookActivityLifecycle);
        super.onTerminate();
    }

    public static class TestLookActivityLifecycle implements ActivityLifecycleCallbacks {

        private static final String TAG = "TestLookActivityLifecycle";

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LogUtils.i(TAG, "onActivityCreated: " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStarted(Activity activity) {
//            LogUtils.i(TAG, "onActivityStarted: " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
//            LogUtils.i(TAG, "onActivityResumed: " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
//            LogUtils.i(TAG, "onActivityPaused: " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
//            LogUtils.i(TAG, "onActivityStopped: " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//            LogUtils.i(TAG, "onActivitySaveInstanceState: " + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtils.i(TAG, "onActivityDestroyed: " + activity.getClass().getSimpleName());
        }
    }
}
