package com.njnu.kai.practice.danmaku.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-8-18
 */
public class ExpPkg {

    @SerializedName("pkg")
    private String mPkg;

    @SerializedName("name")
    private String mName;

    @SerializedName("data")
    private ArrayList<Exp> mExpList;

    private String mFullPkgPath;

    public String getPkg() {
        return mPkg;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Exp> getExpList() {
        return mExpList;
    }

    public String getFullPkgPath() {
        return mFullPkgPath;
    }

    public void setFullPkgPath(String fullPkgPath) {
        mFullPkgPath = fullPkgPath;
    }

    public static class Exp {

        @SerializedName("file")
        private String mFileName;

        @SerializedName("md5")
        private String mMd5;

        private String mPkg;

        public String getPkg() {
            return mPkg;
        }

        public void setPkg(String pkg) {
            mPkg = pkg;
        }

        public String getFileName() {
            return mFileName;
        }

        public String getMd5() {
            return mMd5;
        }
    }
}
