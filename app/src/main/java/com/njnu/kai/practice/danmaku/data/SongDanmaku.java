package com.njnu.kai.practice.danmaku.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-8-18
 */
public class SongDanmaku implements Comparable<SongDanmaku> {

    @SerializedName("e")
    private String mMd5;

    @SerializedName("t")
    private int mTimeMs;

    @SerializedName("pkg")
    private String mPkg;

    @SerializedName("txt")
    private String mText;

    public String getMd5() {
        return mMd5;
    }

    public int getTimeMs() {
        return mTimeMs;
    }

    public String getPkg() {
        return mPkg;
    }

    public String getText() {
        return mText;
    }

    @Override
    public int compareTo(SongDanmaku another) {
        return mTimeMs - another.mTimeMs;
    }
}
