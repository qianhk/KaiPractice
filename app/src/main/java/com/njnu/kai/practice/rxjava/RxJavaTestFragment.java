package com.njnu.kai.practice.rxjava;

import android.os.SystemClock;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-9-17
 */
public class RxJavaTestFragment extends BaseTestListFragment {

    private Disposable mDisposable;


    @TestFunction("throttleWithTimeout")
    public void onTestPosition80() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            if (emitter.isDisposed()) {
                return;
            }
            next(emitter, 1, 0);
            next(emitter, 2, 50);
            next(emitter, 3, 100);
            next(emitter, 4, 30);
            next(emitter, 5, 40);
            next(emitter, 6, 130);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.newThread())
                .throttleWithTimeout(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onComplete() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError: " + e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
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
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            next(emitter, 1, 0);
            next(emitter, 2, 50);
            next(emitter, 3, 50);
            next(emitter, 4, 30);
            next(emitter, 5, 40);
            next(emitter, 6, 130);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.newThread())
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onComplete() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError: " + e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

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
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            next(emitter, 1, 0);
            next(emitter, 2, 50);
            next(emitter, 3, 50);
            next(emitter, 4, 30);
            next(emitter, 5, 40);
            next(emitter, 6, 130);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.newThread())
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onComplete() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("onError: " + e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer index) {
                        System.out.println("== onNext index=" + index + " thread=" + Thread.currentThread().getId());
                        appendResult("onNext index=" + index);
                    }
                });
    }

    private void next(ObservableEmitter<Integer> emitter, int value, int millis) {
        SystemClock.sleep(millis);
        emitter.onNext(value);
    }

    @TestFunction("interval with take, 预期后面的不执行")
    public void onTestPosition70() {
        Observable.interval(1000, TimeUnit.MILLISECONDS).take(5).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onComplete() {
                        appendResult("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        appendResult("Throwable: " + e.toString());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

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

            return Observable.fromArray(integer, integer / 2).delay(delay, TimeUnit.MILLISECONDS);
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

            return Observable.fromArray(integer, integer / 2).delay(delay, TimeUnit.MILLISECONDS);
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

            return Observable.fromArray(integer, integer / 2).delay(delay, TimeUnit.MILLISECONDS);
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> appendResult("flatMap Next:" + integer));

    }

    @TestFunction("timer然后map")
    protected void onTestPosition30() {
        Observable.timer(2000, TimeUnit.MILLISECONDS).map(aLong -> "\nnumber=" + aLong)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::appendResult);
    }

    @TestFunction("zip(interval range) 每隔1秒输出一个数字")
    protected void onTestPosition20() {
        Observable.zip(
                Observable.interval(1, TimeUnit.SECONDS),
                Observable.range(101, 10), (aLong, integer) -> integer)
                .map(aLong -> "next=" + aLong).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onComplete() {
                appendResult("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                appendResult("onError" + e.toString());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                appendResult(s);
            }
        });
    }

    @TestFunction("interval并主动取消")
    protected void onTestPosition10() {
        Observable.interval(1, TimeUnit.SECONDS).map(aLong -> {
            if (aLong < 10) {
                return "next=" + aLong;
            } else {
                return "over";
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onComplete() {
                appendResult("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                appendResult("onError" + e.toString());
            }

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
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
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
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
