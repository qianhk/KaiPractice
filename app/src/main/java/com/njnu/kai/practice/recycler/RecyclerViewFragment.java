package com.njnu.kai.practice.recycler;

import com.njnu.kai.practice.recycler.modal.Category;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;
import com.njnu.kai.support.StateView;

import java.util.ArrayList;
import java.util.Random;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author kai
 * @since 17/1/8
 */
public class RecyclerViewFragment extends PagingListFragment {

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
    protected void onReloadData(int page) {
        if (page == 1) {
            updateListData(makeACategory("from first load " + page, mRandom.nextInt(10)));
            setState(StateView.State.SUCCESS);
            refreshComplete();
        } else {
            makeACategory("from load " + page, mRandom.nextInt(10));
        }
    }
}
