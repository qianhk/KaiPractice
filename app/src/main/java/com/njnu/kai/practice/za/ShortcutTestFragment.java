package com.njnu.kai.practice.za;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.AppRuntime;
import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.FileUtils;
import com.njnu.kai.support.HttpUtils;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.SecurityUtils;
import com.njnu.kai.support.TestFunction;
import com.njnu.kai.support.ToastUtils;
import com.njnu.kai.support.image.BitmapUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kai
 * since 16/12/12
 */
public class ShortcutTestFragment extends BaseTestListFragment {

    private static final String TAG = "ShortcutTestFragment";

    @TestFunction("通过 class name 添加")
    public void onTest1() {
        Intent intent = new Intent(getContext(), ShortcutTestActivity.class);
        intent.putExtra(ShortcutTestActivity.KEY_ID, 1L);
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过 class name 添加");
        addShortcut(getContext(), "ClassName", intent, R.drawable.icon_favorite, true);
    }

    @TestFunction("通过隐式action添加")
    public void onTest2() {
        Intent intent = new Intent();
        intent.setAction("com.njnu.kai.practice.ACTION_SHORTCUT_PAGE");
        intent.putExtra(ShortcutTestActivity.KEY_ID, 2L);
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过隐式action添加");
        addShortcut(getContext(), "隐式Action", intent, R.drawable.icon_favorite, true);
    }

    @TestFunction("通过Scheme添加")
    public void onTest3() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("kaiPractice://page/shortcut"));
        intent.putExtra(ShortcutTestActivity.KEY_ID, 3L);
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme添加");
        addShortcut(getContext(), "Scheme", intent, R.drawable.icon_favorite, true);
    }

    @TestFunction("通过Scheme添加1")
    public void onTest4() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("kaiPractice://page/shortcut1"));
        intent.putExtra(ShortcutTestActivity.KEY_ID, 4L);
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme1添加");
        addShortcut(getContext(), "Scheme1", intent, R.drawable.icon_favorite, true);
    }

    @TestFunction("通过Scheme添加2(两个匹配)")
    public void onTest5() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("kaiPractice://page/shortcut2"));
        intent.putExtra(ShortcutTestActivity.KEY_ID, 5L);
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme1添加");
        addShortcut(getContext(), "Scheme2", intent, R.drawable.icon_favorite, true);
    }

    @TestFunction("通过隐式action添加在线图片")
    public void onTest6() {
        final String picUrl = "http://am.zdmimg.com/201610/01/57ef7c02d00ef.jpg_e600.jpg";
        getBitmapObservable(picUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            if (bitmap == null) {
                ToastUtils.showToast("未能解析图片");
            } else {
                Intent intent = new Intent();
                intent.setAction("com.njnu.kai.practice.ACTION_SHORTCUT_PAGE");
                intent.putExtra(ShortcutTestActivity.KEY_ID, 6L);
                intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过隐式action添加在线图片的主屏图");
                addShortcut(getContext(), "隐式A在线图", intent, bitmap, true);
            }
        }, error -> {
            error.printStackTrace();
            ToastUtils.showToast("添加快捷方式发生错误: " + error.toString());
            LogUtils.e(TAG, "lookShortcut " + error.toString());
        });
    }

    @TestFunction("通过Scheme添加在线图片")
    public void onTest7() {
        final String picUrl = "http://am.zdmimg.com/201609/16/57dbf617cbaf2.jpg_e600.jpg";
        getBitmapObservable(picUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            if (bitmap == null) {
                ToastUtils.showToast("未能解析图片");
            } else {
                Intent intent = new Intent();
                intent.setAction("com.njnu.kai.practice.ACTION_SHORTCUT_PAGE");
                intent.putExtra(ShortcutTestActivity.KEY_ID, 7L);
                intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme添加在线图片的主屏图");
                addShortcut(getContext(), "Scheme在线图", intent, bitmap, true);
            }
        }, error -> {
            error.printStackTrace();
            ToastUtils.showToast("添加快捷方式发生错误: " + error.toString());
            LogUtils.e(TAG, "lookShortcut " + error.toString());
        });
    }

    private Observable<Bitmap> getBitmapObservable(String url) {
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()) {
                return;
            }
            String tmpPath = String.format("%s/app_icon_%s.jpg"
                    , AppRuntime.Storage.getCachePath(getContext()), SecurityUtils.MD5.get16MD5String(url));
            if (!FileUtils.exists(tmpPath)) {
                HttpUtils.downloadByTemporaryFile(url, tmpPath);
            }
            Bitmap bitmap = null;
            if (FileUtils.exists(tmpPath)) {
                bitmap = BitmapUtils.cropBitmapToSquare(tmpPath, 200);
                if (bitmap != null) {
                    LogUtils.i(TAG, "lookShortcut bitmap byteCount=%d width=%d height=%d"
                            , bitmap.getByteCount(), bitmap.getWidth(), bitmap.getHeight());
                }
            }
            subscriber.onNext(bitmap);
            subscriber.onCompleted();
        });
    }

    private static void addShortcut(Context context, String shortcutName, Intent actionIntent
            , int iconResId, boolean allowRepeat) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", allowRepeat);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconResId));
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(shortcutIntent);
    }

    private static void addShortcut(Context context, String shortcutName, Intent actionIntent
            , Bitmap icon, boolean allowRepeat) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", allowRepeat);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(shortcutIntent);
    }

    /*
    Android桌面快捷方式那些事与那些坑
http://blog.zanlabs.com/2015/03/14/android-shortcut-summary/

android 创建快捷方式的两种方式+判断是否已经创建+删除快捷方式
http://blog.csdn.net/panda1234lee/article/details/9042873

     */
}
