package com.njnu.kai.practice.image;

import android.graphics.Bitmap;
import android.os.Environment;

import com.njnu.kai.practice.image.compress.Compressor;
import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/12
 */
public class ImageCompressFragment extends BaseTestListFragment {

    private static final int MAX_FILE_SIZE = 4 * 1024 * 1024;

    private File mOriFile;

    private File prepareOriginalFile() {
        if (mOriFile == null) {
            String dcimPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

            File file = new File(dcimPath + "/camera/");
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        return false;
                    }
                    if (pathname.length() > MAX_FILE_SIZE) {
                        return true;
                    }
                    return false;
                }
            });
            mOriFile = files[0];
        }
        return mOriFile;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.getDefault(), "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private String makeResultString(File oriFile, File newFile) {
        return String.format(Locale.getDefault()
                , "ori=%s oriSize=%s newSize=%s", oriFile.getName()
                , humanReadableByteCount(oriFile.length(), false)
                , humanReadableByteCount(newFile.length(), false));
    }

    @TestFunction("Luban 压缩图片 Use listener")
    public void onTest01() {
//        File file = prepareOriginalFile();
//        Luban.with(getContext())
//                .load(file)                     //传人要压缩的图片
//                .setCompressListener(new OnCompressListener() { //设置回调
//
//                    @Override
//                    public void onStart() {
//                        appendResult("onStart");
//                    }
//
//                    @Override
//                    public void onSuccess(File newFile) {
//                        appendResult(makeResultString(file, newFile));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        appendResult(e.getMessage());
//                    }
//                }).launch();
    }

    @TestFunction("Luban 压缩图片 use rxJava")
    public void onTest02() {
//        File file = prepareOriginalFile();
//        Flowable.just(file)
//                .observeOn(Schedulers.io())
//                .map(oriFile -> Luban.with(getActivity()).load(oriFile).get())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(newFile -> appendResult(makeResultString(file, newFile)), this::appendResult);
    }

    @TestFunction("Compressor 压缩图片  use rxJava")
    public void onTest03() {
        File file = prepareOriginalFile();
        Flowable.create(new FlowableOnSubscribe<File>() {
            @Override
            public void subscribe(FlowableEmitter<File> e) throws Exception {
                Compressor compressor = new Compressor(getActivity())
//                        .setMaxWidth(1500)
//                        .setMaxHeight(1500)
                        .setDestinationDirectoryPath(getContext().getExternalCacheDir().getAbsolutePath())
                        .setCompressFormat(Bitmap.CompressFormat.WEBP);
                e.onNext(compressor.compressToFile(file));
                e.onComplete();
            }
        }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newFile -> appendResult(makeResultString(file, newFile)), this::appendResult);
    }

    @TestFunction("Compressor 压缩图片 default size、quality")
    public void onTest04() {
        File file = prepareOriginalFile();
        new Compressor(getContext())
//                .setMaxWidth(1500)
//                .setMaxHeight(1500)
//                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(getContext().getExternalCacheDir().getAbsolutePath())
                .compressToFileAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newFile -> appendResult(makeResultString(file, newFile)), this::appendResult);
    }

    @TestFunction("Compressor 压缩图片  quality 80 ")
    public void onTest05() {
        File file = prepareOriginalFile();
        new Compressor(getContext())
//                .setMaxWidth(1500)
//                .setMaxHeight(1500)
//                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setQuality(80)
                .setDestinationDirectoryPath(getContext().getExternalCacheDir().getAbsolutePath())
                .compressToFileAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newFile -> appendResult(makeResultString(file, newFile)), this::appendResult);
    }
}
