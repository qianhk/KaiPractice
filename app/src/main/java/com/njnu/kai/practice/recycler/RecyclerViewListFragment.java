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
import me.drakeet.multitype.BaseVO;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.OnMultiTypeViewListener;

/**
 * @author kai
 * @since 17/1/8
 */
abstract public class RecyclerViewListFragment extends StateViewFragment implements LoadStateCallback, OnMultiTypeViewListener {
    private static final String TAG = "RecyclerViewListFragment";

    private PtrFrameLayout mPtrFrameLayout;

    private RecyclerView mRecyclerView;

    private MultiTypeAdapter mAdapter;

    private LoadStateCallback mCallback = this;
    private Pager mPager = new Pager();
    private boolean mLoading = true;
    private boolean mErrorFromSecondPage = false;
    private LoadingFooterView mFooterView;

    protected int listViewLayoutId() {
        return needPtrAndLoadNextFeature() ? R.layout.common_recyclerview_with_ptr : R.layout.common_recyclerview_without_ptr;
    }

    protected void onContentViewInflated(View contentView) {
    }

    abstract protected void onAdapterCreated(MultiTypeAdapter adapter);

    protected boolean needPtrAndLoadNextFeature() {
        return true;
    }

//    protected boolean needLoadNextPageFeature() {
//        return true;
//    }


    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mainView = layoutInflater.inflate(listViewLayoutId(), viewGroup, false);
        mPtrFrameLayout = (PtrFrameLayout) mainView.findViewById(R.id.ptr_frame_layout);
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.recycler_view);
        onContentViewInflated(mainView);

        mAdapter = new MultiTypeAdapter();
        mAdapter.setMultiTypeViewListener(this);
        mAdapter.applyGlobalMultiTypePool();
        onAdapterCreated(mAdapter);

        if (mPtrFrameLayout != null) {
            mFooterView = new LoadingFooterView(layoutInflater.getContext());
            mFooterView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT
                    , RecyclerView.LayoutParams.WRAP_CONTENT));
            mRecyclerView.setAdapter(new WrapperAdapter(mAdapter, mFooterView));

            mFooterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mLoading) {
                        mFooterView.setState(LoadingFooterView.State.Loading);
                        prepareReloadData(mPager.next(), false);
                    }
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
                            mFooterView.setState(LoadingFooterView.State.TheEnd);
                            return;
                        }

                        if (mErrorFromSecondPage) {
//                        mCallback.onLoadDataComplete(mPager.getCurrent(), false);
                            LogUtils.d(TAG, "lookLoad load failed from second page");
                            ToastUtils.showToast("非第一页加载失败");
                            return;
                        }

                        mFooterView.setState(LoadingFooterView.State.Loading);
                        prepareReloadData(mPager.next(), true);
                    }
                }
            });

        } else {
            mRecyclerView.setAdapter(mAdapter);
        }

        if (mPtrFrameLayout != null) {
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
        }

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
        mErrorFromSecondPage = false;
        onLoadDataComplete(totalPage, true);
        if (mPager.isStartPage()) {
            mPager.setTotal(totalPage);
            mAdapter.flushData(data);
            if (mAdapter.isEmpty()) {
                mCallback.onStateChanged(StateView.State.NO_DATA, 0);
            } else {
                mCallback.onStateChanged(StateView.State.SUCCESS, 0);
            }
            if (totalPage == 1) {
                mCallback.onNoMoreData();
            }
        } else {
            int dataCount = data != null ? data.size() : 0;
            if (dataCount == 0) {
                if (mAdapter.isEmpty()) {
                    mCallback.onStateChanged(StateView.State.NO_DATA, 0);
                } else {
                    mPager.moveToNext();
                }
            } else {
                mPager.moveToNext();
                mAdapter.appendData(data);
            }
        }
    }

    @Override
    protected void onRetryRequested() {
        setState(StateView.State.LOADING);
        prepareReloadData(Pager.DEFAULT_PAGE_START, false);
    }

    public void handleLoadDataFailed() {
        if (!isAdded()) {
            return;
        }
        onLoadDataComplete(0, false);
        if (mPager.isStartPage()) {
            mAdapter.flushData(null);
            mCallback.onStateChanged(StateView.State.FAILED, 0);
        } else {
            if (mAdapter.isEmpty()) {
                mCallback.onStateChanged(StateView.State.FAILED, 0);
            } else {
                mErrorFromSecondPage = true;
            }
        }
    }

    protected void refreshComplete() {
        if (mPtrFrameLayout != null) {
            mPtrFrameLayout.refreshComplete();
        }
    }

    @Override
    public void onLoadDataComplete(int page, boolean success) {
        //尾巴如何显示
        mLoading = false;
        refreshComplete();
        if (mFooterView != null) {
            mFooterView.setState(success ? LoadingFooterView.State.Normal : LoadingFooterView.State.NetWorkError);
        }
    }

    @Override
    public void onNoMoreData() {
        //尾巴如何显示
        if (mFooterView != null) {
            mFooterView.setState(LoadingFooterView.State.TheEnd);
        }
    }

    @Override
    public void onStateChanged(StateView.State state, int code) {
        setState(state);
    }

    @Override
    public void onMultiTypeValueChanged(BaseVO data, String action) {

    }

    public BaseVO getItemDataByVoId(String voId) {
        return mAdapter != null ? mAdapter.getItemDataByVoId(voId) : null;
    }

    @Override
    public void onMultiTypeViewClicked(BaseVO data, String action) {
        ToastUtils.showToast("click button: " + data.getClass().getSimpleName());
    }

    public void handleVoRemoved(BaseVO baseVO) {
        mAdapter.removeData(baseVO);
    }

    public void handleVoUpdated(BaseVO baseVO) {
        if (baseVO == null) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.dataUpdated(baseVO);
        }
    }
}
