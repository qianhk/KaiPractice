package com.njnu.kai.practice.image;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/12
 */
public class ImageCompressFragment extends BaseTestListFragment {

    @TestFunction("压缩图片 Use listener")
    public void onTest01() {
        File file = new File("/sdcard/dcim/camera/IMG_20170412_195631.jpg");
        Luban.get(getContext())
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        appendResult("onStart");
                    }

                    @Override
                    public void onSuccess(File file) {
                        appendResult(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult(e.getMessage());
                    }
                }).launch();
    }

    @TestFunction("压缩图片 use rxJava")
    public void onTest02() {
        File file = new File("/sdcard/dcim/camera/IMG_20170412_195631.jpg");
        Luban.get(getContext())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        appendResult(throwable.getMessage());
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                    @Override
                    public Observable<? extends File> call(Throwable throwable) {
                        return Observable.empty();
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        appendResult(file.getAbsolutePath());
                    }
                });
    }
}
