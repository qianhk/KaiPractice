package com.njnu.kai.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.njnu.kai.practice.bind.BindTestEntryFragment;
import com.njnu.kai.practice.danmaku.DanmaukuActivity;
import com.njnu.kai.practice.di.TestDaggerFragment;
import com.njnu.kai.practice.entry.EntryAnimatorActivity;
import com.njnu.kai.practice.guest.GuestActivity;
import com.njnu.kai.practice.image.ImageTestEntryFragment;
import com.njnu.kai.practice.material.CoordinatorLayoutActivity;
import com.njnu.kai.practice.material.VectorDrawableActivity;
import com.njnu.kai.practice.material.composite.MaterialCompositeActivity;
import com.njnu.kai.practice.material.reveal.RevealActivity;
import com.njnu.kai.practice.retrofit.RetrofitEntryFragment;
import com.njnu.kai.practice.rxjava.RxJavaTestFragment;
import com.njnu.kai.practice.splash.SplashActivity;
import com.njnu.kai.support.ConstantUtils;
import com.njnu.kai.support.FunctionItem;
import com.njnu.kai.support.ListViewUtils;
import com.njnu.kai.support.ProxyActivity;
import com.njnu.kai.support.base.ActionBarLayoutFragment;
import com.njnu.kai.support.base.BaseFragment;
import com.njnu.kai.support.list.DragUpdateListView;
import com.njnu.kai.practice.za.EditTextOutputFragment;

import java.util.ArrayList;

public class MainFragment extends ActionBarLayoutFragment implements AdapterView.OnItemClickListener {

    private DragUpdateListView mListView;

    private static final ArrayList<FunctionItem> FUNCTIONS = new ArrayList<FunctionItem>();

    static {
        FUNCTIONS.add(new FunctionItem("Image loader2", ImageTestEntryFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Retrofit Test", RetrofitEntryFragment.class, false));
        FUNCTIONS.add(new FunctionItem("RxJava Test", RxJavaTestFragment.class, false));
        FUNCTIONS.add(new FunctionItem("EditText output", EditTextOutputFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Data Binding", BindTestEntryFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Dagger2", TestDaggerFragment.class, false));
        FUNCTIONS.add(new FunctionItem("Song Danmaku", DanmaukuActivity.class, true));
        FUNCTIONS.add(new FunctionItem("TweenAnimation", TweenAnimationActivity.class, true));
        FUNCTIONS.add(new FunctionItem("FrameAnimation", FrameAnimationActivity.class, true));
        FUNCTIONS.add(new FunctionItem("PropertyAnimation", PropertyAnimationActivity.class, true));
        FUNCTIONS.add(new FunctionItem("Chicken Animator", AnimatorActivity.class, true));
        FUNCTIONS.add(new FunctionItem("WebView", WebViewActivity.class, true));
        FUNCTIONS.add(new FunctionItem("SplashTest", SplashActivity.class, true));
        FUNCTIONS.add(new FunctionItem("EntryAnimator", EntryAnimatorActivity.class, true));
        FUNCTIONS.add(new FunctionItem("RevealActivity", RevealActivity.class, true));
        FUNCTIONS.add(new FunctionItem("MaterialComposite(5.0)", MaterialCompositeActivity.class, true));
        FUNCTIONS.add(new FunctionItem("VectorDrawable", VectorDrawableActivity.class, true));
        FUNCTIONS.add(new FunctionItem("GuestActivity", GuestActivity.class, true));
        FUNCTIONS.add(new FunctionItem("CoordinatorLayout", CoordinatorLayoutActivity.class, true));
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

}