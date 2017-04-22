package com.njnu.kai.practice.dex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.njnu.kai.practice.recycler.ColorDividerItemDecoration;
import com.njnu.kai.practice.recycler.RecyclerViewListFragment;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;
import com.njnu.kai.practice.util.Utils;
import com.njnu.kai.support.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;
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

    private static final String TAG = "DexTestFragment";

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
                try {
                    subscriber.onNext(getClassNameList());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<List<String>, List<Text>>() {
            @Override
            public List<Text> call(List<String> strings) {
                ArrayList<Text> list = new ArrayList<>();
                if (strings != null) {
                    for (String string : strings) {
                        if (string.startsWith("com.njnu")) {
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
        getRecyclerView().addItemDecoration(new ColorDividerItemDecoration(Color.LTGRAY, 1, RecyclerView.VERTICAL));
    }

    private List<String> getClassNameList() throws PackageManager.NameNotFoundException, IOException {
        Context context = getContext();
        PackageManager packageManager = context.getPackageManager();
        String packageCodePath = context.getPackageCodePath(); //  /data/app/com.njnu.kai.practice-1/base.apk
        String packageResourcePath = context.getPackageResourcePath(); //  /data/app/com.njnu.kai.practice-1/base.apk
        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        String sourceDirFileStr = applicationInfo.publicSourceDir; //    /data/app/com.njnu.kai.practice-1/base.apk

        ArrayList<String> list = new ArrayList<>();

//        File incomeFile = File.createTempFile("classes" + Thread.currentThread().getId(), ".dex", getCacheDir());
//        IOUtils.bytesToFile(bytes, incomeFile);

        File incomeFile = new File(sourceDirFileStr);

//        String optimizedFileStr = Environment.getExternalStorageDirectory().getPath() + File.separator + "optKaiTEst.dex";
//        //IllegalArgumentException: Optimized data directory /storage/emulated/0 is not owned by the current user. Shared storage cannot protect your application from code injection attacks.

        File optimizedFile = File.createTempFile("opt" + Thread.currentThread().getId(), ".dex", context.getCacheDir());
        LogUtils.d(TAG, "optimizedFile path=" + optimizedFile.getPath());
        DexFile dx = DexFile.loadDex(incomeFile.getPath(), optimizedFile.getPath(), 0);

        for (Enumeration<String> classNames = dx.entries(); classNames.hasMoreElements(); ) {
            String className = classNames.nextElement();
            list.add(className);
        }

        return list;
    }
}
