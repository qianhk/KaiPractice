package com.njnu.kai.practice.dex;

import android.content.Context;
import android.os.Environment;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/12
 */
public class LookClassLoaderFragment extends BaseTestListFragment {

    private DexClassLoader mMockClickClassLoader;

    @TestFunction("Fragment加载")
    public void onTest01() {
        ClassLoader classLoader = getClass().getClassLoader();
        setResult("fragent: " + classLoader.getClass().getName());  //dalvik.system.PathClassLoader
        while (classLoader.getParent() != null) {
            classLoader = classLoader.getParent();
            appendResult("parent: " + classLoader.getClass().getName());  //java.lang.BootClassLoader
        }
    }

    @TestFunction("POJO加载")
    public void onTest02() throws ClassNotFoundException { //Plain Old Java Object
        ClassLoader classLoader = Class.forName("com.njnu.kai.practice.dex.Person").getClassLoader();
        setResult("pojo: " + classLoader.getClass().getName());  //dalvik.system.PathClassLoader
        while (classLoader.getParent() != null) {
            classLoader = classLoader.getParent();
            appendResult("parent: " + classLoader.getClass().getName());  //java.lang.BootClassLoader
        }
    }

    @TestFunction("KaiClassLoader Person")
    public void onTest03() {
//        try {
//            KaiClassLoader kaiClassLoader = new KaiClassLoader();
//            Class<?> person2 = kaiClassLoader.loadClass("com.njnu.kai.compiled.Person");
//            if (person2 != null) {
//                ClassLoader classLoader = person2.getClassLoader();
//                setResult("onTest03 load success, loader is " + classLoader.getClass().getName());
//                while (classLoader.getParent() != null) {
//                    classLoader = classLoader.getParent();
//                    appendResult("parent: " + classLoader.getClass().getName());  //java.lang.BootClassLoader
//                }
//            } else {
//                setResult("onTest03 load failed");
//            }
//        } catch (Exception e) {
//            //UnsupportedOperationException can't load this type of class file
//            //http://blog.csdn.net/jiangwei0910410003/article/details/17679823
//            appendResult(e);
//        }
    }


    @TestFunction("DexLassLoader")
    public void onTest04() {
        try {
            String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "dexTest/KaiPersonDex.jar";
            //String dexOutputDir = getApplicationInfo().dataDir;
            File optimizedDexOutputPath = getActivity().getDir("outdex", Context.MODE_PRIVATE);
            DexClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedDexOutputPath.getAbsolutePath()
                    , null, getClass().getClassLoader());
            Class personClass = dexClassLoader.loadClass("com.njnu.kai.compiled.Person");
            if (personClass != null) {
                Object person = personClass.newInstance();
                setResult("person class load success: " + person);
            } else {
                setResult("person class load failed");
            }
        } catch (Exception e) {
            //ava.lang.IllegalArgumentException: Optimized data directory /sdcard/dexTest is not owned by the current user.
            // Shared storage cannot protect your application from code injection attacks
            appendResult(e);
        }
    }

    @TestFunction("加载mockClick apk")
    public void onTest05() {
        try {
            if (mMockClickClassLoader == null) {
                String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "dexTest/mockClick.apk";
                //String dexOutputDir = getApplicationInfo().dataDir;
                File optimizedDexOutputPath = getActivity().getDir("outdex", Context.MODE_PRIVATE);
                mMockClickClassLoader = new DexClassLoader(dexPath, optimizedDexOutputPath.getAbsolutePath()
                        , null, getClass().getClassLoader());
            }
            Class mainActivityClass = mMockClickClassLoader.loadClass("com.njnu.kai.mockclick.MainActivity");
            if (mainActivityClass != null) {
                Object mainActivity = mainActivityClass.newInstance();
                setResult("mainActivityClass load success: " + mainActivity);
            } else {
                setResult("mainActivityClass load failed");
            }
        } catch (Exception e) {
            appendResult(e);
        }
    }

}
