package com.njnu.kai.practice.dex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.njnu.kai.support.LogUtils;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

import static com.njnu.kai.support.ProxyActivity.FROM;
import static com.njnu.kai.support.ProxyActivity.FROM_EXTERNAL;

/**
 * @author kai
 * @since 2017/6/3
 */
public class ProxyActivity extends AppCompatActivity {

    public static final String KEY_APK_PATH = "key_apk_path";
    public static final String KEY_ClASS = "key_class";
    private static final String TAG = "ProxyActivity";

    private String mApkPath;
    private String mClass;

    public static void startup(Activity activity, String apkPath, String clazz) {
        Intent intent = new Intent(activity, ProxyActivity.class);
        intent.putExtra(KEY_APK_PATH, apkPath);
        intent.putExtra(KEY_ClASS, clazz);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApkPath = getIntent().getStringExtra(KEY_APK_PATH);
        mClass = getIntent().getStringExtra(KEY_ClASS);

        loadResources();

        if (mClass == null) {
            launchTargetActivity();
        } else {
            launchTargetActivity(mClass);
        }
    }

    @SuppressLint("NewApi")
    protected void launchTargetActivity() {
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(mApkPath, PackageManager.GET_ACTIVITIES);
        if ((packageInfo.activities != null) && (packageInfo.activities.length > 0)) {
            String activityName = packageInfo.activities[0].name;
            mClass = activityName;
            launchTargetActivity(mClass);
        }
    }

    @SuppressLint("NewApi")
    protected void launchTargetActivity(final String className) {
        LogUtils.d(TAG, "start launchTargetActivity, className=" + className);
        File dexOutputDir = this.getDir("dex", 0);
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(mApkPath, dexOutputPath, null, localClassLoader);
        try {
            Class<?> localClass = dexClassLoader.loadClass(className);
//            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
//            Object instance = localConstructor.newInstance(new Object[] {});
            Object instance = localClass.newInstance();
            LogUtils.d(TAG, "instance = " + instance);

//            Method setProxy = localClass.getMethod("setProxy", Activity.class);
//            setProxy.setAccessible(true);
//            setProxy.invoke(instance, this);

            Method onCreate = localClass.getDeclaredMethod("onCreate", Bundle.class);
            onCreate.setAccessible(true);
            Bundle bundle = new Bundle();
            bundle.putInt(FROM, FROM_EXTERNAL);
            onCreate.invoke(instance, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;

    protected void loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mApkPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = super.getResources();
        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }
}
