package com.njnu.kai.practice;


import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.njnu.kai.practice.aidl.AidlTestFragment;
import com.njnu.kai.practice.aidl.AidlTestService;
import com.njnu.kai.practice.recycler.MultiTypeInstaller;
import com.njnu.kai.support.AppRuntime;
import com.njnu.kai.support.BaseApplication;
import com.njnu.kai.support.image.ImageLoader;

import java.io.File;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 14-4-29
 */
public class MainApplication extends BaseApplication {

    public static final boolean TEST_CONTINUE_CRASH = false;

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(AppRuntime.Storage.getCachePath(this) + File.separator + "imageTest");
//        Stetho.initializeWithDefaults(this);
        AndroidThreeTen.init(this);
        MultiTypeInstaller.start();


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
    }
}
