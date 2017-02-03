package com.njnu.kai.practice.recycler;

import com.njnu.kai.practice.recycler.modal.ButtonVO;
import com.njnu.kai.practice.recycler.modal.Category;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;

import java.util.ArrayList;
import java.util.Random;

import me.drakeet.multitype.BaseVO;
import me.drakeet.multitype.MultiTypeAdapter;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.ToastUtils;

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

    public static ArrayList<Object> makeACategory(int page, String title, int subCount) {
        ArrayList<Object> dataList = new ArrayList<>();
        Category category = new Category(title);
        category.mVoId = "category" + page;
        dataList.add(category);
        for (int idx = 1; idx <= subCount; ++idx) {
            dataList.add(new Text(title.hashCode() + "_" + idx));
        }
        dataList.add(new ButtonVO("测试按钮", 0xFFFF8000, R.drawable.ic_ptr_bkg));
        return dataList;
    }

    @Override
    public void onReloadData(final int page, final boolean auto) {
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page == Pager.DEFAULT_PAGE_START) {
                    handleLoadDataSuccess(makeACategory(page, "from first load " + page, 16), 5);
                } else {
                    handleLoadDataSuccess(makeACategory(page, "from load " + page, 8), 0);
                }
            }
        }, 2000);
    }

    @Override
    public void onMultiTypeViewClicked(BaseVO data, String action) {
        super.onMultiTypeViewClicked(data, action);
        BaseVO vo = getItemDataByVoId("category" + 2);
        ToastUtils.showToast("click button: category2=" + (vo != null ? vo.getClass().getSimpleName() : null));
    }
}
