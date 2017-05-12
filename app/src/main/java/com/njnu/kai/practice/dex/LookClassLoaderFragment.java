package com.njnu.kai.practice.dex;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/12
 */
public class LookClassLoaderFragment extends BaseTestListFragment {

    @TestFunction("Fragment加载")
    public void onTest01() {
        ClassLoader classLoader = getClass().getClassLoader();
        setResult("fragent: " + classLoader.getClass().getName());  //dalvik.system.PathClassLoader
        while(classLoader.getParent() != null) {
            classLoader = classLoader.getParent();
            appendResult("parent: " + classLoader.getClass().getName());  //java.lang.BootClassLoader
        }
    }

    @TestFunction("POJO加载")
    public void onTest02() throws ClassNotFoundException { //Plain Old Java Object
        ClassLoader classLoader = Class.forName("com.njnu.kai.practice.dex.Person").getClassLoader();
        setResult("pojo: " + classLoader.getClass().getName());  //dalvik.system.PathClassLoader
        while(classLoader.getParent() != null) {
            classLoader = classLoader.getParent();
            appendResult("parent: " + classLoader.getClass().getName());  //java.lang.BootClassLoader
        }
    }


}
