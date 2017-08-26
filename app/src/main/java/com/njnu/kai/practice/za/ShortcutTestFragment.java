package com.njnu.kai.practice.za;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.util.LauncherUtils;
import com.njnu.kai.support.AppRuntime;
import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.FileUtils;
import com.njnu.kai.support.HttpUtils;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.SDKVersionUtils;
import com.njnu.kai.support.SecurityUtils;
import com.njnu.kai.support.StringUtils;
import com.njnu.kai.support.TestFunction;
import com.njnu.kai.support.ToastUtils;
import com.njnu.kai.support.image.BitmapUtils;

import java.io.ByteArrayOutputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by kai
 * since 16/12/12
 */
public class ShortcutTestFragment extends BaseTestListFragment {

    private static final String TAG = "ShortcutTestFragment";
    private static final String sFavoritePathAndParameter = "/favorites?notify=true";

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
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme2添加");
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

    @TestFunction("是否已创建快捷方式")
    public void onTest8() {
        Context context = getContext();
        StringBuilder builder = new StringBuilder();
        builder.append("ClassName: " + isShortcutExist(context, "ClassName"));
        builder.append("\n隐式Action: " + isShortcutExist(context, "隐式Action"));
        builder.append("\nScheme: " + isShortcutExist(context, "Scheme"));
        builder.append("\nScheme1: " + isShortcutExist(context, "Scheme1"));
        builder.append("\nScheme2: " + isShortcutExist(context, "Scheme2"));
        builder.append("\n隐式A在线图: " + isShortcutExist(context, "隐式A在线图"));
        builder.append("\nScheme在线图: " + isShortcutExist(context, "Scheme在线图"));
        setResult(builder.toString());
    }

    @TestFunction("更新'通过Scheme添加1'的图标")
    public void onTest9() {
        final String picUrl = "http://am.zdmimg.com/201609/29/57eca38a15436.jpg_e600.jpg";
        getBitmapObservable(picUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> {
            if (bitmap == null) {
                ToastUtils.showToast("未能解析图片");
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("kaiPractice://page/shortcut1"));
                intent.putExtra(ShortcutTestActivity.KEY_ID, 4L);
                intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme1添加");
                updateShortcutIcon(getContext(), "Scheme1", intent, bitmap);
            }
        }, error -> {
            error.printStackTrace();
            ToastUtils.showToast("更新快捷方式发生错误: " + error.toString());
            LogUtils.e(TAG, "lookShortcut " + error.toString());
        });
    }

    @TestFunction("删除'通过Scheme添加1'的图标")
    public void onTestA() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("kaiPractice://page/shortcut1"));
        intent.putExtra(ShortcutTestActivity.KEY_ID, 4L);
        intent.putExtra(ShortcutTestActivity.KEY_TWEET, "通过Scheme1添加");
        deleteShortcut(getContext(), "Scheme1", intent, true, R.drawable.icon_favorite);
    }

    public static void deleteShortcut(Context context, String title, Intent intent, boolean duplicate, int iconResId) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", duplicate);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconResId));
        context.sendBroadcast(shortcutIntent);
    }

    /**
     * 更新桌面快捷方式图标，不一定所有图标都有效<br/>
     * 如果快捷方式不存在，则不更新<br/>.
     */
    public static void updateShortcutIcon(Context context, String title, Intent intent, Bitmap bitmap) {
        if (bitmap == null) {
            LogUtils.i(TAG, "update shortcut icon, bitmap empty");
            return;
        }
        try {
            final Uri CONTENT_URI = getShotcutContentUri(context);
            String intentUri = intent.toUri(0);
//            Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"_id", "title", "intent"},
//                    "title=?  and intent=? ",
//                    new String[]{title, intentUri}, null);
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"_id", "title", "intent"},
                    "title=?", new String[]{title}, null); //带上intent查询不到,原因待调研
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int index = cursor.getInt(0);//获得图标索引
                ContentValues cv = new ContentValues();
                cv.put("icon", flattenBitmap(bitmap));
                String urlTemp = CONTENT_URI.toString();
                urlTemp = urlTemp.substring(0, urlTemp.length() - sFavoritePathAndParameter.length());
                Uri oneUri = Uri.parse(urlTemp + "/favorites/" + index + "?notify=true");
                int affectedCount = context.getContentResolver().update(oneUri, cv, null, null);
                context.getContentResolver().notifyChange(CONTENT_URI, null);//此处不能用oneUri，是个坑
                LogUtils.i(TAG, "update ok: affected " + affectedCount + " rows,index is" + index);
            } else {
                LogUtils.i(TAG, "update result failed");
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //java.lang.SecurityException: Permission Denial: writing com.miui.home.launcher.LauncherProvider uri content://com.miui.home.launcher.settings/favorites/125?notify=true
            // from pid=6751, uid=10130 requires com.android.launcher.permission.WRITE_SETTINGS, or grantUriPermission()
            LogUtils.i(TAG, "update shortcut icon, get errors:" + ex.getMessage());
            ToastUtils.showToast(ex.getMessage());
        }
    }


    private static byte[] flattenBitmap(Bitmap bitmap) {
        // Try go guesstimate how much space the icon will take when serialized
        // to avoid unnecessary allocations/copies during the write.
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            LogUtils.w(TAG, "Could not write icon");
            return null;
        }
    }

    /**
     * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br/>
     * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br/>
     * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br/>
     * 此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
     * 错误信息已捕获<br/>
     */
    public static boolean isShortcutExist(Context context, String title) {
        boolean isInstallShortcut = false;
        try {
            final Uri CONTENT_URI = getShotcutContentUri(context);
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?", new String[]{title}, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    isInstallShortcut = true;
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInstallShortcut;
    }

    private static Uri getShotcutContentUri(Context context) {
        String authority = LauncherUtils.getAuthorityFromPermissionDefault(context);
        if (StringUtils.isEmpty(authority)) {
            authority = LauncherUtils.getAuthorityFromPermission(context, LauncherUtils.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
        }
        if (StringUtils.isEmpty(authority)) {
            if (SDKVersionUtils.hasKitKat()) { // 4.4及以上
                authority = "com.android.launcher3.settings";
            } else if (SDKVersionUtils.hasFroyo()) { //2.2及以上
                authority = "com.android.launcher2.settings";
            } else {
                authority = "com.android.launcher.settings";
            }
        }
        return Uri.parse("content://" + authority + sFavoritePathAndParameter);
    }

    private Observable<Bitmap> getBitmapObservable(String url) {
        return Observable.create(emitter -> {
            if (emitter.isDisposed()) {
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
            emitter.onNext(bitmap);
            emitter.onComplete();
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
