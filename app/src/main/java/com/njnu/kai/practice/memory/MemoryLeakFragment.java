package com.njnu.kai.practice.memory;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import java.util.Locale;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/3/17
 */
public class MemoryLeakFragment extends BaseTestListFragment {

    @TestFunction("创建1000个ImageView")
    public void onTest01() {
        for (int i = 0; i < 1000; i++) {
            View view = new ImageView(getActivity());
        }
    }

    @TestFunction("创建1000个ImageView,主动gc")
    public void onTest02() {
        for (int i = 0; i < 1000; i++) {
            View view = new ImageView(getActivity());
        }
        System.gc();// 4.x可以 art不一定 5.x不一定 仅标记
        System.runFinalization();
//        Runtime.getRuntime().gc();
    }

    @TestFunction("register Receiver but not unregister")
    public void onTest03() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(new TestBroadcastReceiver(), new IntentFilter("xxxxAction"));
        //如果TestBroadcastReceiver是静态类,不会泄露MemoryLeakFragment本身,receiveRecord持有TestBroadcastReceiver
        //但如果不是静态类, 则会泄露MemoryLeakFragment也会被receiver持有
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);

        int memoryClass = activityManager.getMemoryClass();
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        setResult(String.format(Locale.getDefault(), "memory=%d largeMemory=%d", memoryClass, largeMemoryClass));
        //mi 3s : 192 384

    }

    private class TestBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
