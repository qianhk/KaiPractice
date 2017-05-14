package com.njnu.kai.practice.dex;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import dalvik.system.DexClassLoader;

/**
 * @author kai
 * @since 2017/5/13
 */
public class KaiClassLoader extends DexClassLoader {

    public KaiClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(new File(String.format("/sdcard/%s.class", name)));
        if (classData == null) {
            super.findClass(name); // 未找到，抛异常
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
        return null;
    }

    private byte[] getClassData(File file) {
        try {
            FileInputStream ins = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
