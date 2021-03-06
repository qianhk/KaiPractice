package com.njnu.kai.practice.dex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.njnu.kai.practice.recycler.ColorDividerItemDecoration;
import com.njnu.kai.practice.recycler.RecyclerViewListFragment;
import com.njnu.kai.practice.recycler.modal.Text;
import com.njnu.kai.practice.recycler.provider.TextItemViewProvider;
import com.njnu.kai.practice.za.TextViewerFragment;
import com.njnu.kai.support.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.BaseVO;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/22
 */
public class DexTestFragment extends RecyclerViewListFragment {

    private static final String TAG = "DexTestFragment";

    private DexClassLoader mDexClassLoader;

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
        Observable.create((ObservableOnSubscribe<List<String>>) emitter -> {
            emitter.onNext(getClassNameList());
            emitter.onComplete();
        }).map((Function<List<String>, List<Text>>) strings -> {
            ArrayList<Text> list = new ArrayList<>();
            if (strings != null) {
                for (String string : strings) {
                    if (string.startsWith("com.njnu")) {
                        list.add(new Text(string, true));
                    }
                }
            }
            return list;
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<List<Text>>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                handleLoadDataFailed();
            }

            @Override
            public void onSubscribe(Disposable d) {

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

    @Override
    public void onMultiTypeViewClicked(BaseVO data, String action) {
//        super.onMultiTypeViewClicked(data, action);
        if (mDexClassLoader == null || !(data instanceof Text)) {
            return;
        }
        Text text = (Text) data;
        try {
            Class<?> loadClass = mDexClassLoader.loadClass(text.mText);
            Reflector reflector = new Reflector(loadClass);
            reflector.generateClassData();
            String result = reflector.toString();

//            LogUtils.i(TAG, result);
            TextViewerFragment.launch(this, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getClassNameList() throws Exception {
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

        mDexClassLoader = DexLoaderBuilder.fromFile(getContext(), incomeFile);
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
