package com.njnu.kai.practice.retrofit;

import com.njnu.kai.practice.retrofit.data.MovieEntity;
import com.njnu.kai.practice.retrofit.data.UserInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/4/24
 */
public interface MovieService {

    String KEY_START = "keyStart";

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("top250?testkey=10")
    Call<MovieEntity> getTopMovie(@Query(KEY_START) int start, @Query("count") int count);

    @Headers("KaiHeader:      kaiValue") //似乎整理过，这样写不会有多余的空格发出去
    @GET("top250")
    Observable<MovieEntity> getTopMovieObservable(@Header("Authorization") String authorization, @Query("start") int start, @Query("count") int count);

    @GET("{No}")
    Observable<MovieEntity> getTopMovieObservableByPath(@Path("No") String no, @Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("top250")
    Observable<MovieEntity> getTopMovieByMap(@FieldMap Map<String, String> options);

    @Headers("KaiHeader: kaiValue")
    @FormUrlEncoded
    @POST("top250")
    Observable<MovieEntity> getTopMovieByIds(@Field("id") List<Long> taskIds); //输出不合理 id=10&id=20&id=30

    @POST("top250")
    Observable<MovieEntity> getTopMovieWithBody(@Body UserInfo userInfo); //@Body 只能Post

    @Multipart
    @POST("top250")
    Observable<MovieEntity> getTopMovieWithPart(@Part("photo") RequestBody photo, @Part("uid") String uid);

    @Multipart
    @POST("top250")
    Observable<MovieEntity> getTopMovieWithMultiPart(@Part("photo") RequestBody photo);

    @Multipart
    @POST("top250")
    Observable<MovieEntity> uploadWithPart(@Part("image.png") RequestBody photo
            , @Part("description") RequestBody description);

}
