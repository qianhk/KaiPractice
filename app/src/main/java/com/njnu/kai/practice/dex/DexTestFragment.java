package com.njnu.kai.practice.dex;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.njnu.kai.practice.recycler.ColorDividerItemDecoration;
import com.njnu.kai.practice.recycler.RecyclerViewListFragment;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;
import com.njnu.kai.practice.util.Utils;
import com.njnu.kai.support.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/22
 */
public class DexTestFragment extends RecyclerViewListFragment {

    @Override
    protected boolean needPtrAndLoadNextFeature() {
        return false;
    }

    @Override
    protected void onAdapterCreated(MultiTypeAdapter adapter) {
        adapter.register(Text.class, new TextItemViewProvider());
    }

    @Override
    public void onReloadData(int page, boolean auto) {
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(getClassNameList());
                subscriber.onCompleted();
            }
        }).map(new Func1<List<String>, List<Text>>() {
            @Override
            public List<Text> call(List<String> strings) {
                ArrayList<Text> list = new ArrayList<>();
                if (strings != null) {
                    for (String string : strings) {
                        if (!Utils.isJavaInnerClass(string)) {
                            list.add(new Text(string));
                        }
                    }
                }
                return list;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Subscriber<List<Text>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                handleLoadDataFailed();
            }

            @Override
            public void onNext(List<Text> list) {
                handleLoadDataSuccess(list, 1);
            }
        });
    }

    @Override
    protected void onContentViewInflated(View contentView) {
        super.onContentViewInflated(contentView);
        getRecyclerView().addItemDecoration(new ColorDividerItemDecoration(Color.LTGRAY, DisplayUtils.dp2px(1), RecyclerView.VERTICAL));
    }

    private List<String> getClassNameList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("haha");
        list.add("haha2");
        return list;
    }
}
