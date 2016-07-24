package com.njnu.kai.practice.retrofit;


import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.njnu.kai.practice.retrofit.data.MovieEntity;
import com.njnu.kai.practice.retrofit.data.UserInfo;
import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.HttpUtils;
import com.njnu.kai.support.JSONUtils;
import com.njnu.kai.support.TestFunction;
import com.njnu.kai.support.executor.RxExecutor;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import java.io.File;
import java.util.ArrayList;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/4/24
 */
public class RetrofitEntryFragment extends BaseTestListFragment {

    private static final String BASE_URL = "http://api.douban.com/v2/movie/";

    private Retrofit mRetrofit;

    private MovieService mMovieService;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpUtils.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(JSONUtils.gsonInstance()))
                .build();

        mMovieService = mRetrofit.create(MovieService.class);
    }

    @TestFunction("Test Base Method use call.enqueue")
    private void on00() {
        Call<MovieEntity> call = mMovieService.getTopMovie(0, 10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                setResult(Thread.currentThread().getId() + "\n" + response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                setResult(t.getMessage());
            }
        });
    }

    @TestFunction("Test Base Method use Observable")
    private void on01() {
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieObservable("headerByParameter", 0, 5);
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("getTopMovieByIds(POST)")
    private void on04() {
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(10L);
        longs.add(20L);
        longs.add(30L);
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieByIds(longs);
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("getTopMovieObservableByPath")
    private void on05() {
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieObservableByPath("top250", 0, 5);
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("@QueryMap @FieldMap Map<String, String> options")
    private void on06() {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        arrayMap.put("start", "0");
        arrayMap.put("count", "2");
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieByMap(arrayMap);
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("@Body 只能Post 且只能单用")
    private void on07() {
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieWithBody(UserInfo.makeMockData(3));
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("Multipart @Part  okhttp3.FormBody (不好 应该只上传文件)")
    private void on08() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key1", "v&a l%u:e1%20\"");
        builder.addEncoded("key2", "v&a&nbsp;l%u:e2%20\""); //仅对%不再转移
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieWithPart(builder.build(), "uid" + 567_89);
//        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieWithPart(builder.build(), null);
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("Multipart @Part  okhttp3.MultipartBody (不好的格式，产生双重multipart嵌套)")
    private void on09() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("key1", "value1");
        builder.addFormDataPart("key2", "value2");
        builder.addFormDataPart("uid", "uid" + 567_89);
        Observable<MovieEntity> topMovieObservable = mMovieService.getTopMovieWithMultiPart(builder.build());
        RxExecutor.execute(topMovieObservable, this::setResult);
    }

    @TestFunction("Multipart @Part 上传文件、流")
    private void on10() {
        String descriptionString = "hello, this is description speaking";
//        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "/sdcard/qhk.trc");
        RequestBody requestBody = RequestBody.create(null, new File("/sdcard/qhk.trc"));
        RequestBody description = RequestBody.create(null, descriptionString);
        Observable<MovieEntity> topMovieObservable = mMovieService.uploadWithPart(requestBody, description);
        RxExecutor.execute(topMovieObservable, this::setResult);
    }
}
