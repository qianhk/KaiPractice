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

    @TestFunction("KaiClassLoader Person2")
    public void onTest03() {
        try {
            KaiClassLoader kaiClassLoader = new KaiClassLoader();
            Class<?> person2 = kaiClassLoader.loadClass("Person2");
            if (person2 != null) {
                ClassLoader classLoader = person2.getClassLoader();
                setResult("person2 load success, loader is " + classLoader.getClass().getName());
                while (classLoader.getParent() != null) {
                    classLoader = classLoader.getParent();
                    appendResult("parent: " + classLoader.getClass().getName());  //java.lang.BootClassLoader
                }
            } else {
                setResult("person2 load failed");
            }
        } catch (Exception e) {
            //UnsupportedOperationException can't load this type of class file
            //http://blog.csdn.net/jiangwei0910410003/article/details/17679823
            appendResult(e);
        }
    }

}
