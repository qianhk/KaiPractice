package com.njnu.kai.practice.recycler;

import com.njnu.kai.support.StateView;

/**
 * @author kai
 * @since 17/1/15
 */
public interface LoadStateCallback {

    /**
     * 去加载数据吧,注意这是主线程调用的
     *
     * @param page 加载第几页
     * @param auto 是否自动加载,比如加载错误时用户点击重试的时候false,下拉刷新到底部时自动加载则true
     */
    void onReloadData(final int page, final boolean auto);

    /**
     * 加载数据完成
     *
     * @param page    第几页
     * @param success 成功与否
     */
    void onLoadDataComplete(int page, boolean success);

    /**
     * 加载到最后一页没有更多数据了
     */
    void onNoMoreData();

    /**
     * 供stateview显示状态
     *
     * @param state 状态
     * @param code  失败code,暂时只对失败状态有效
     */
    void onStateChanged(StateView.State state, int code);

}
