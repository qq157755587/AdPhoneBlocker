package com.zhaoyuanjie.adphoneblocker

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

/**
 * Created by zhaoyuanjie on 15/12/26.
 */
interface BaiduAPI {

    @GET("phone_information_query")
    fun query(@Query("tel") tel: String, @Query("location") location: Boolean): Call<QueryResult>
}