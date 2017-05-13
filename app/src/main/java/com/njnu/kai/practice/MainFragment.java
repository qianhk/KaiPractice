package com.njnu.kai.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.njnu.kai.practice.aidl.AidlTestFragment;
import com.njnu.kai.practice.dex.DexTestFragment;
import com.njnu.kai.practice.dex.LookClassLoaderFragment;
import com.njnu.kai.practice.draw.MultiLineViewFragment;
import com.njnu.kai.practice.guideanimator.ChickenAnimatorActivity;
import com.njnu.kai.practice.animator.FrameAnimationActivity;
import com.njnu.kai.practice.animator.PropertyAnimationActivity;
import com.njnu.kai.practice.animator.SignalViewFragment;
import com.njnu.kai.practice.animator.TweenAnimationActivity;
import com.njnu.kai.practice.animator.circle.LoadingAnimatorFragment;
import com.njnu.kai.practice.animator.circle.PullToRefreshFragment;
import com.njnu.kai.practice.animator.circle.XfermodeFragment;
import com.njnu.kai.practice.bind.BindTestEntryFragment;
import com.njnu.kai.practice.danmaku.DanmaukuActivity;
import com.njnu.kai.practice.di.TestDaggerFragment;
import com.njnu.kai.practice.entry.EntryAnimatorActivity;
import com.njnu.kai.practice.guest.GuestActivity;
import com.njnu.kai.practice.image.ImageCompressFragment;
import com.njnu.kai.practice.image.ImageTestEntryFragment;
import com.njnu.kai.practice.image.shadow.ShadowImageFragment;
import com.njnu.kai.practice.location.AMap2DTestFragment;
import com.njnu.kai.practice.location.AMapLocationTestFragment;
import com.njnu.kai.practice.lock.LockTestFragment;
import com.njnu.kai.practice.material.CoordinatorLayoutActivity;
import com.njnu.kai.practice.material.VectorDrawableActivity;
import com.njnu.kai.practice.material.composite.MaterialCompositeActivity;
import com.njnu.kai.practice.material.reveal.RevealActivity;
import com.njnu.kai.practice.memory.MemoryLeakFragment;
import com.njnu.kai.practice.pinyin.HanziToPinyinFragment;
import com.njnu.kai.practice.recycler.RecyclerViewTestFragment;
import com.njnu.kai.practice.retrofit.RetrofitEntryFragment;
import com.njnu.kai.practice.rxjava.RxJavaTestFragment;
import com.njnu.kai.practice.scroll.ManageTwoScrollFragment;
import com.njnu.kai.practice.scroll.PullRefreshFragment;
import com.njnu.kai.practice.splash.SplashActivity;
import com.njnu.kai.practice.ui.ConstraintLayoutFragment;
import com.njnu.kai.practice.draw.PeriscopeAnimatorFragment;
import com.njnu.kai.practice.za.EditTextOutputFragment;
import com.njnu.kai.practice.za.EditTextStatusFragment;
import com.njnu.kai.practice.za.ShortcutTestFragment;
import com.njnu.kai.practice.za.WebSocketTestFragment;
import com.njnu.kai.support.ConstantUtils;
import com.njnu.kai.support.FunctionItem;
import com.njnu.kai.support.ListViewUtils;
import com.njnu.kai.support.ProxyActivity;
import com.njnu.kai.support.base.ActionBarLayoutFragment;
import com.njnu.kai.support.base.BaseFragment;
import com.njnu.kai.support.list.DragUpdateListView;

import java.util.ArrayList;

public class MainFragment extends ActionBarLayoutFragment implements AdapterView.OnItemClickListener {

    private DragUpdateListView mListView;

    private static final ArrayList<FunctionItem> FUNCTIONS = new ArrayList<>();

    static {
        FUNCTIONS.add(new FunctionItem("Image loader", ImageTestEntryFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Retrofit Test", RetrofitEntryFragment.class, false));
        FUNCTIONS.add(new FunctionItem("RxJava Test", RxJavaTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Nested Scroll", PullRefreshFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Nested Scroll2", ManageTwoScrollFragment.class, false));
        FUNCTIONS.add(new FunctionItem("AMap Location", AMapLocationTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("AMap 2D", AMap2DTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Memory Leak", MemoryLeakFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Loading Animator", LoadingAnimatorFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Pull To Refresh", PullToRefreshFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Xfermode", XfermodeFragment.class, false));
        FUNCTIONS.add(new FunctionItem("ShortCut", ShortcutTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("RecyclerView", RecyclerViewTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("EditText output", EditTextOutputFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Data Binding", BindTestEntryFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Dagger2", TestDaggerFragment.class, false));
        FUNCTIONS.add(new FunctionItem("WebSocket", WebSocketTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("AIDL Test", AidlTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Song Danmaku", DanmaukuActivity.class, true));
        FUNCTIONS.add(new FunctionItem("Periscope", PeriscopeAnimatorFragment.class, false));
        FUNCTIONS.add(new FunctionItem("TweenAnimation", TweenAnimationActivity.class, true));
        FUNCTIONS.add(new FunctionItem("FrameAnimation", FrameAnimationActivity.class, true));
        FUNCTIONS.add(new FunctionItem("PropertyAnimation", PropertyAnimationActivity.class, true));
        FUNCTIONS.add(new FunctionItem("Chicken Animator", ChickenAnimatorActivity.class, true));
        FUNCTIONS.add(new FunctionItem("Draw View", SignalViewFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Color Shadow Image", ShadowImageFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Lock Time Test", LockTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("WebView Test", WebViewFragment.class, false));
        FUNCTIONS.add(new FunctionItem("SplashTest", SplashActivity.class, true));
        FUNCTIONS.add(new FunctionItem("EntryAnimator", EntryAnimatorActivity.class, true));
        FUNCTIONS.add(new FunctionItem("RevealActivity", RevealActivity.class, true));
        FUNCTIONS.add(new FunctionItem("MaterialComposite(5.0)", MaterialCompositeActivity.class, true));
        FUNCTIONS.add(new FunctionItem("VectorDrawable", VectorDrawableActivity.class, true));
        FUNCTIONS.add(new FunctionItem("GuestActivity", GuestActivity.class, true));
        FUNCTIONS.add(new FunctionItem("CoordinatorLayout", CoordinatorLayoutActivity.class, true));
        FUNCTIONS.add(new FunctionItem("EditTextStatus", EditTextStatusFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Hanzi To Pinyin", HanziToPinyinFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Image Compress", ImageCompressFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Constraint Layout", ConstraintLayoutFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Line Multi", MultiLineViewFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Look Class Loader", LookClassLoaderFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Dex loader", DexTestFragment.class, false));
    }

    @Override
    protected void onInitActionBar() {
        super.onInitActionBar();
        setTitle(R.string.app_name);
    }

    @Override
    protected boolean needBackAction() {
        return false;
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_listview, container, false);
        mListView = (DragUpdateListView) view.findViewById(R.id.listview);
        BaseAdapter arrayAdapter = new TrainMainAdapter(getActivity(), FUNCTIONS);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setEnableDragUpdate(false);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position = ListViewUtils.getValidListViewItemPosition(mListView.getHeaderViewsCount(), position, mListView.getAdapter().getCount());
        FunctionItem functionItem = FUNCTIONS.get(position);
        gotoPage(functionItem);
    }

    private void gotoPage(FunctionItem functionItem) {
        if (functionItem.isActivity()) {
            Intent intent = new Intent(getActivity(), functionItem.getActionClass());
            intent.putExtra(ProxyActivity.KEY_TITLE, functionItem.getName());
            startActivity(intent);
        } else {
            try {
                BaseFragment fragment = (BaseFragment) functionItem.getActionClass().newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtils.KEY_PAGE_TITLE, functionItem.getName());
                fragment.setArguments(bundle);
                launchFragment(fragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FunctionItem functionItem = null;
        for (FunctionItem function : FUNCTIONS) {
            if (function.getActionClass() == LookClassLoaderFragment.class) {
                functionItem = function;
                break;
            }
        }
        if (functionItem != null) {
            gotoPage(functionItem);
        }
    }

}
