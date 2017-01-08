package com.njnu.kai.practice.recycler;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.AppRuntime;
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
abstract public class PagingListFragment extends StateViewFragment {

    private PtrFrameLayout mPtrFrameLayout;

    private RecyclerView mRecyclerView;

    private MultiTypeAdapter mAdapter;

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
                onReloadData(1);
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, content, header);
            }
        });

        return mainView;
    }

    abstract protected void onReloadData(int page);

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
            onReloadData(1);
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

    public void appendListData(List<?> data) {
        if (data != null && data.size() > 0) {
            mAdapter.appendData(data);
        }
    }

    public void updateListData(List<?> data) {
        int size = data != null ? data.size() : 0;
        mAdapter.flushData(data);
    }

    protected void refreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }

}
