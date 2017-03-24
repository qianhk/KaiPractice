package com.njnu.kai.practice.rxjava;

import android.os.SystemClock;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-9-17
 */
public class RxJavaTestFragment extends BaseTestListFragment {

    private Subscription mTestSubscribe;


    @TestFunction("throttleWithTimeout")
    public void onTestPosition80() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                next(subscriber, 1, 0);
                next(subscriber, 2, 50);
                next(subscriber, 3, 100);
                next(subscriber, 4, 30);
                next(subscriber, 5, 40);
                next(subscriber, 6, 130);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .throttleWithTimeout(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer index) {
                        System.out.println("== onNext index=" + index + " thread=" + Thread.currentThread().getId());
                        appendResult("onNext index=" + index);
                    }
                });
    }

    @TestFunction("throttleFirst")
    public void onTestPosition81() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                next(subscriber, 1, 0);
                next(subscriber, 2, 50);
                next(subscriber, 3, 50);
                next(subscriber, 4, 30);
                next(subscriber, 5, 40);
                next(subscriber, 6, 130);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer index) {
                        System.out.println("== onNext index=" + index + " thread=" + Thread.currentThread().getId());
                        appendResult("onNext index=" + index);
                    }
                });
    }

    @TestFunction("throttleLast")
    public void onTestPosition82() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                next(subscriber, 1, 0);
                next(subscriber, 2, 50);
                next(subscriber, 3, 50);
                next(subscriber, 4, 30);
                next(subscriber, 5, 40);
                next(subscriber, 6, 130);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer index) {
                        System.out.println("== onNext index=" + index + " thread=" + Thread.currentThread().getId());
                        appendResult("onNext index=" + index);
                    }
                });
    }

    private void next(Subscriber<? super Integer> subscriber, int value, int millis) {
        SystemClock.sleep(millis);
        subscriber.onNext(value);
    }

    @TestFunction("interval with take, 预期后面的不执行")
    public void onTestPosition70() {
        Observable.interval(1000, TimeUnit.MILLISECONDS).take(5).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                appendResult("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                appendResult("Throwable: " + e.toString());
            }

            @Override
            public void onNext(Long aLong) {
                appendResult("onNext: " + aLong);
            }
        });
    }

    @TestFunction("flatMap")
    protected void onTestPosition60() {
        //flatMap操作符的运行结果
        Observable.just(8, 20, 30).flatMap(integer -> {
            //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
            int delay = 1000;
            if (integer > 10)
                delay = 500;

            return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
            appendResult("flatMap Next:" + integer);
        });

    }

    @TestFunction("concatMap")
    protected void onTestPosition50() {
        //concatMap
        Observable.just(8, 20, 30).concatMap(integer -> {
            //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
            int delay = 1000;
            if (integer > 10)
                delay = 500;

            return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
            appendResult("flatMap Next:" + integer);
        });

    }

    @TestFunction("switchMap")
    protected void onTestPosition40() {
        //switchMap
        Observable.just(8, 20, 30).switchMap(integer -> {
            //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
            int delay = 1000;
            if (integer > 10)
                delay = 500;

            return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
            appendResult("flatMap Next:" + integer);
        });

    }

    @TestFunction("timer然后map")
    protected void onTestPosition30() {
        mTestSubscribe = Observable.timer(2000, TimeUnit.MILLISECONDS).map(aLong -> "\nnumber=" + aLong)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::appendResult);
    }

    @TestFunction("zip(interval range) 每隔1秒输出一个数字")
    protected void onTestPosition20() {
        Observable.zip(
                Observable.interval(1, TimeUnit.SECONDS),
                Observable.range(101, 10), (aLong, integer) -> integer)
                .map(aLong -> "next=" + aLong).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                appendResult("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                appendResult("onError" + e.toString());
            }

            @Override
            public void onNext(String s) {
                appendResult(s);
            }
        });
    }

    @TestFunction("interval并主动取消")
    protected void onTestPosition10() {
        mTestSubscribe = Observable.interval(1, TimeUnit.SECONDS).map(aLong -> {
            if (aLong < 10) {
                return "next=" + aLong;
            } else {
                return "over";
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        appendResult(s);
                        if (s.equals("over")) {
                            cancelTestSubscribe();
                        }
                    }
                });
    }

    @TestFunction("just filter map")
    private void onTestPosition01() {
        Observable.just("Hello world", "hello2", "hello3")
//                .map(s -> s.hashCode())
//                .map(s -> String.valueOf(s))
                .filter(s -> !s.contains("3"))
                .map(s -> s + "\n")
                .subscribe(this::appendResult);
    }

    private void cancelTestSubscribe() {
        if (mTestSubscribe != null) {
            mTestSubscribe.unsubscribe();
            mTestSubscribe = null;
        }
    }

    @Override
    protected void beforeTest() {
        super.beforeTest();
        cancelTestSubscribe();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelTestSubscribe();
    }
}
