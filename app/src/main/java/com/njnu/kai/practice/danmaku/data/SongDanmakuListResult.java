package com.njnu.kai.practice.danmaku.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-8-18
 */
public class SongDanmakuListResult {

    @SerializedName("code")
    private int mCode;

    @SerializedName("data")
    private ArrayList<SongDanmaku> mSongDanmakuList;

    public int getCode() {
        return mCode;
    }

    public ArrayList<SongDanmaku> getSongDanmakuList() {
        return mSongDanmakuList;
    }
}
