package com.njnu.kai.practice.recycler;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.AppRuntime;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.StateView;
import com.njnu.kai.support.StateViewFragment;
import com.njnu.kai.support.ToastUtils;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author kai
 * @since 17/1/8
 */
abstract public class PagingListFragment extends StateViewFragment implements LoadStateCallback {

    private static final String TAG = "PagingListFragment";

    private PtrFrameLayout mPtrFrameLayout;

    private RecyclerView mRecyclerView;

    private MultiTypeAdapter mAdapter;


    private LoadStateCallback mCallback = this;
    private Pager mPager = new Pager();
    private boolean mLoading = true;
    private boolean mErrorFromSecondPage = false;

    protected int listViewLayoutId() {
        return R.layout.common_recyclerview;
    }

    protected void onContentViewInflated(View contentView) {
    }

    abstract protected void onAdapterCreated(MultiTypeAdapter adapter);

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mainView = layoutInflater.inflate(listViewLayoutId(), viewGroup, false);
        mPtrFrameLayout = (PtrFrameLayout) mainView.findViewById(R.id.ptr_frame_layout);
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.recycler_view);
        onContentViewInflated(mainView);

        mAdapter = new MultiTypeAdapter();
        mAdapter.applyGlobalMultiTypePool();
        onAdapterCreated(mAdapter);

        mRecyclerView.setAdapter(mAdapter);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                if (!AppRuntime.Network.isNetWorkAvailable()) {
                    ToastUtils.showToast(R.string.network_unavailable);
                    ptrFrameLayout.refreshComplete();
                    return;
                }
                mPager = new Pager();
                prepareReloadData(Pager.DEFAULT_PAGE_START, false);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, content, header);
            }
        });
        mRecyclerView.addOnScrollListener(new OnLoadMoreScrollListener() {
            @Override
            public void onLoadMore(RecyclerView recyclerView) {
                if (!mLoading) {
                    if (!mPager.hasNext()) {
                        mCallback.onNoMoreData();
                        LogUtils.d(TAG, "lookLoad no more data");
                        ToastUtils.showToast("没有更多数据了");
                        return;
                    }

                    if (mErrorFromSecondPage) {
//                        mCallback.onLoadDataComplete(mPager.getCurrent(), false);
                        LogUtils.d(TAG, "lookLoad load failed from second page");
                        ToastUtils.showToast("非第一页加载失败");
                        return;
                    }

                    prepareReloadData(mPager.next(), true);
                }
            }
        });

        return mainView;
    }

    private void prepareReloadData(final int page, final boolean auto) {
        mLoading = true;
        final int pageWantLoad = page < 0 ? mPager.next() : page;
//        mCallback.onStartLoadData(pageWantLoad);
        LogUtils.d(TAG, "lookLoad load data, page=%d", page);
        mCallback.onReloadData(page, auto);
    }

    @Override
    protected void onLoadFinished() {
        super.onLoadFinished();
        if (enterAutoLoading()) {
//            List<> cacheData = onReloadCacheData();
//            if (cacheData != null && !cacheData.isEmpty()) {
//                mAdapter.flushData(cacheData);
//                setState(StateView.State.SUCCESS);
//            }
//            mPagingHelper.prepareReloadData(Pager.DEFAULT_PAGE_START, true);
            prepareReloadData(Pager.DEFAULT_PAGE_START, true);
        }
    }

    @Override
    protected void onRetryRequested() {

    }

    protected boolean enterAutoLoading() {
        return true;
    }

    @Override
    protected final StateView.State originalState() {
        return enterAutoLoading() ? StateView.State.LOADING : StateView.State.SUCCESS;
    }

//    public void appendListData(List<?> data) {
//        if (data != null && data.size() > 0) {
//            mAdapter.appendData(data);
//        }
//    }
//
//    public void updateListData(List<?> data) {
//        int size = data != null ? data.size() : 0;
//        mAdapter.flushData(data);
//    }

    public void handleLoadDataSuccess(List<?> data, int totalPage) {
        if (!isAdded()) {
            return;
        }
        mLoading = false;
        mPtrFrameLayout.refreshComplete();
        int dataCount = data != null ? data.size() : 0;
        if (dataCount == 0) {
            if (mAdapter.isEmpty()) {
                mCallback.onStateChanged(StateView.State.NO_DATA, 0);
            } else {
                mPager.moveToNext();
            }
        } else {
            if (mPager.isStartPage()) {
                mPager.setTotal(totalPage);
                mAdapter.flushData(data);
                mCallback.onStateChanged(StateView.State.SUCCESS, 0);
            } else {
                mPager.moveToNext();
                mAdapter.appendData(data);
            }
        }
    }

    public void handleLoadDataFailed() {
        if (!isAdded()) {
            return;
        }
        mLoading = false;
        mPtrFrameLayout.refreshComplete();
        if (mAdapter.isEmpty()) {
            mCallback.onStateChanged(StateView.State.FAILED, 0);
        } else {
            mErrorFromSecondPage = true;
        }
    }

    protected void refreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoadDataComplete(int page, boolean success) {
        //尾巴如何显示
    }

    @Override
    public void onNoMoreData() {
        //尾巴如何显示
    }

    @Override
    public void onStateChanged(StateView.State state, int code) {
        setState(state);
    }
}
