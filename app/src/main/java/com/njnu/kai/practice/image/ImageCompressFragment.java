package com.njnu.kai.practice.image;

import android.graphics.Bitmap;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import org.reactivestreams.Subscription;

import java.io.File;

import id.zelory.compressor.Compressor;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/12
 */
public class ImageCompressFragment extends BaseTestListFragment {

    @TestFunction("Luban 压缩图片 Use listener")
    public void onTest01() {
        File file = new File("/sdcard/dcim/camera/IMG_20170412_195631.jpg");
        Luban.with(getContext())
                .load(file)                     //传人要压缩的图片
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

    @TestFunction("Luban 压缩图片 use rxJava")
    public void onTest02() {
        File file = new File("/sdcard/dcim/camera/IMG_20170412_195631.jpg");
        Flowable.just(file)
                .observeOn(Schedulers.io())
                .map(oriFile -> Luban.with(getActivity()).load(oriFile).get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newFile -> appendResult(newFile.getCanonicalPath()), this::appendResult);
    }

    @TestFunction("Compressor 压缩图片  use rxJava")
    public void onTest03() {
        File file = new File("/sdcard/dcim/camera/IMG_20170412_195631.jpg");
        Flowable.create(new FlowableOnSubscribe<File>() {
            @Override
            public void subscribe(FlowableEmitter<File> e) throws Exception {
                Compressor compressor = new Compressor(getActivity())
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
//                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .setCompressFormat(Bitmap.CompressFormat.WEBP);
                e.onNext(compressor.compressToFile(file));
                e.onComplete();
            }
        }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newFile -> appendResult(file.getAbsolutePath()), this::appendResult);
    }

    @TestFunction("Compressor 压缩图片  use rxJava max size")
    public void onTest04() {
        File file = new File("/sdcard/dcim/camera/IMG_20170412_195631.jpg");
        new Compressor(getContext())
                .setMaxWidth(1200)
                .setMaxHeight(1500)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setDestinationDirectoryPath("/sdcard/")
                .compressToFileAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newFile -> appendResult(file.getAbsolutePath()), this::appendResult);
    }
}
