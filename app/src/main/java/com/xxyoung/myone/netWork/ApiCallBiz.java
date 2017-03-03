package com.xxyoung.myone.netWork;


import com.xxyoung.myone.bean.IdListBean;
import com.xxyoung.myone.bean.ReadDetailBean;
import com.xxyoung.myone.bean.ReadingListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YX on 16/7/14.
 */
public interface ApiCallBiz {
//    @GET("data/Android/{number}/{page}")
//    Observable<DataInfoBean> getAndroidInfo(@Path("number") int number, @Path("page") int page);

    //    @POST("zj_cloud/mobileLogin")
//    @FormUrlEncoded
//    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);

//    @FormUrlEncoded
    @GET("onelist/idlist")
    Observable<IdListBean> getIdList(@Query("channel") String channel, @Query("version") String version, @Query("uuid") String uuid, @Query("platform") String platform);

    @GET("channel/reading/more/{page}")
    Observable<ReadingListBean> getReadingList(@Path("page") String page, @Query("channel") String channel, @Query("version") String version, @Query("uuid") String uuid, @Query("platform") String platform);

    @GET("essay/{page}")
    Observable<ReadDetailBean> getReadDetail(@Path("page") String page, @Query("channel") String channel, @Query("version") String version, @Query("uuid") String uuid, @Query("platform") String platform);


}
