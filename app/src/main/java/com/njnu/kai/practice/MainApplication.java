package com.njnu.kai.practice;


import com.jakewharton.threetenabp.AndroidThreeTen;
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

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(AppRuntime.Storage.getCachePath(this) + File.separator + "imageTest");
//        Stetho.initializeWithDefaults(this);
        AndroidThreeTen.init(this);
    }
}
