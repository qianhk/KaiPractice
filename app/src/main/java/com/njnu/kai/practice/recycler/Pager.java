package com.njnu.kai.practice.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Pager {

    public static final int DEFAULT_PAGE_START = 1;
    private static final int DEFAULT_PAGE_TOTAL = 1;

    private int mStart = DEFAULT_PAGE_START;
    private int mCurrent = mStart;
    private int mTotal = DEFAULT_PAGE_TOTAL;
    private boolean mStartPage = true;

    /**
     * 设置开始页，默认为1
     * @param startIndex 开始页
     */
    public void setStart(int startIndex) {
        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex must >= 0");
        }
        mStart = startIndex;
    }

    /**
     * 设置总页数，默认为1
     * @param total 总页数
     */
    public void setTotal(int total) {
        if (total < 1) {
            total = 1;
        }
        mTotal = total;
    }

    /**
     * 设置当前请求页
     * @param currentIndex 当前请求页
     */
    public void setCurrent(int currentIndex) {
        if (currentIndex < mStart || currentIndex > end()) {
            throw new IllegalArgumentException("currentIndex must be >= PageStart and <= pageEnd");
        }
        mCurrent = currentIndex;
    }

    /**
     * 获取当前页
     * @return 当前页
     */
    public int getCurrent() {
        return mCurrent;
    }

    /**
     * 获取开始页
     * @return 开始页
     */
    public int getStart() {
        return mStart;
    }

    /**
     * 获取总页数
     * @return  总页数
     */
    public int getTotal() {
        return mTotal;
    }

    /**
     * 请求下一页数据
     * @return 下一页
     */
    public int next() {
        mStartPage = false;
        return mCurrent + 1;
    }

    /**
     * 移动标志位到下一个位置
     */
    public void moveToNext() {
        mCurrent++;
        mStartPage = false;
    }

    /**
     * 是否已经过了最后一页
     * @param pageIndex 请求的页码
     * @return 是否已经过了最后一夜
     */
    public boolean isOver(int pageIndex) {
        return pageIndex > end();
    }

    /**
     * @return 判断是否为第一次加载
     */
    public boolean isStartPage() {
        return mStartPage && mCurrent == mStart;
    }

    /**
     * 最后一页页码
     * @return 最后一页页码
     */
    public int end() {
        return mStart + mTotal - 1;
    }

    /**
     *
     * @return 是否还有下一页
     */
    public boolean hasNext() {
        return mCurrent < mTotal;
    }

}
