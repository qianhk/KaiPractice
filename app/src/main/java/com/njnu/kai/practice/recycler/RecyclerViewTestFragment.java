package com.njnu.kai.practice.recycler;

import com.njnu.kai.practice.recycler.modal.Category;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;

import java.util.ArrayList;
import java.util.Random;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author kai
 * @since 17/1/8
 */
public class RecyclerViewTestFragment extends RecyclerViewListFragment {

    private Random mRandom = new Random(System.currentTimeMillis());

//    @Override
//    protected void onInitActionBar() {
//        super.onInitActionBar();
//        addImageAction(0, R.drawable.ic_launcher);
//    }
//
//    @Override
//    protected void onActionClick(int actionId, ActionBarLayout.Action action) {
//        super.onActionClick(actionId, action);
//        ++mSeed;
//        mAdapter.appendData(makeACategory("from Action " + mSeed, mSeed));
//    }

    @Override
    protected void onAdapterCreated(MultiTypeAdapter adapter) {
        adapter.register(Text.class, new TextItemViewProvider());
    }

    public static ArrayList<Object> makeDataList() {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.addAll(makeACategory("First Category", 5));
        dataList.addAll(makeACategory("Second Category", 3));
        return dataList;
    }

    public static ArrayList<Object> makeACategory(String title, int subCount) {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.add(new Category(title));
        for (int idx = 1; idx <= subCount; ++idx) {
            dataList.add(new Text(title.hashCode() + "_" + idx));
        }
        return dataList;
    }

    @Override
    public void onReloadData(final int page, final boolean auto) {
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page == Pager.DEFAULT_PAGE_START) {
                    handleLoadDataSuccess(makeACategory("from first load " + page, 16), 5);
                } else {
                    handleLoadDataSuccess(makeACategory("from load " + page, 8), 0);
                }
            }
        }, 2000);
    }

}
