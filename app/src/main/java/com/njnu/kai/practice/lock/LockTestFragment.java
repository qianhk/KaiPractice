package com.njnu.kai.practice.lock;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author kai
 * @since 17/4/4
 */
public class LockTestFragment extends BaseTestListFragment {

    public static final int N = 6;
    private Executor mExecutor;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExecutor = new ThreadPoolExecutor(8, 8, 5L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(16));
    }

    @TestFunction("CountDownLatch等待任务都结束")
    public void onTest01() {
        CountDownLatch doneSignal = new CountDownLatch(N);

        setResult(nowTime() + "begin test01");
        for (int i = 0; i < N; ++i) { // create and start threads
            mExecutor.execute(new WorkerRunnable1(doneSignal, i));
        }
        mExecutor.execute(new WaitRunnable(doneSignal));
    }

    @TestFunction("CountDownLatch等待一起开始等待结束")
    public void onTest02() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mExecutor = null;
    }

    private class WorkerRunnable1 implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;

        WorkerRunnable1(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        public void run() {
            doWork();
            doneSignal.countDown();
            appendAsync(i + ": end work");
        }

        void doWork() {
            appendAsync(i + ": begin work");
            SystemClock.sleep(i * 1000);
        }
    }

    private class WaitRunnable implements Runnable {

        private final CountDownLatch doneSignal;

        private WaitRunnable(CountDownLatch doneSignal) {
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                appendAsync("await");
                doneSignal.await();
                appendAsync("await finish");
            } catch (InterruptedException e) {
                appendAsync("await exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static String nowTime() {
        Calendar instance = Calendar.getInstance();
        return String.format(Locale.getDefault(), "%02d:%02d.%03d "
                , instance.get(Calendar.MINUTE), instance.get(Calendar.SECOND), instance.get(Calendar.MILLISECOND));
    }

    public void appendAsync(String text) {
        getView().post(new Runnable() {
            @Override
            public void run() {
                appendResult(nowTime() + text);
            }
        });
    }
}
