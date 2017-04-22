package com.njnu.kai.practice.util;

import java.util.ArrayList;

/**
 * @version 1.0.0
 */
public class Utils {

    private static final ArrayList<String> INNER_CLASS_PREFIX_LIST;

    static {
        INNER_CLASS_PREFIX_LIST = new ArrayList<String>();
        INNER_CLASS_PREFIX_LIST.add("android.");
        INNER_CLASS_PREFIX_LIST.add("java.");
        INNER_CLASS_PREFIX_LIST.add("org.");
        INNER_CLASS_PREFIX_LIST.add("javax.");
        INNER_CLASS_PREFIX_LIST.add("com.android.");
        INNER_CLASS_PREFIX_LIST.add("junit.");
        INNER_CLASS_PREFIX_LIST.add("dalvik.");
    }

    public static boolean isJavaInnerClass(String className) {
        if (className == null || className.indexOf('.') < 0) {
            return false;
        }
        for (String classPrefix : INNER_CLASS_PREFIX_LIST) {
            if (className.startsWith(classPrefix)) {
                return true;
            }
        }
        return false;
    }
}
