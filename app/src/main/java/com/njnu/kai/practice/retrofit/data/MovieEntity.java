package com.njnu.kai.practice.retrofit.data;

import com.google.gson.annotations.SerializedName;
import com.njnu.kai.support.JSONUtils;

import java.util.List;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/4/24
 */
public class MovieEntity {

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : []
     * title : 豆瓣电影Top250
     */

    @SerializedName("count")
    private int mCount;
    @SerializedName("start")
    private int mStart;
    @SerializedName("total")
    private int mTotal;
    @SerializedName("title")
    private String mTitle;
//    @SerializedName("subjects")
//    private List<?> mSubjects;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int start) {
        mStart = start;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

//    public List<?> getSubjects() {
//        return mSubjects;
//    }
//
//    public void setSubjects(List<?> subjects) {
//        mSubjects = subjects;
//    }

    @Override
    public String toString() {
        return JSONUtils.toJsonString(this);
    }
}
