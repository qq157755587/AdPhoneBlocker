package com.zhaoyuanjie.adphoneblocker

import retrofit.GsonConverterFactory
import retrofit.Retrofit

/**
 * http://apistore.baidu.com/apiworks/servicedetail/1398.html
 * Created by zhaoyuanjie on 15/12/26.
 */
object Restful {

    private fun createRetrofit(): Retrofit {
        val retrofitClient: Retrofit = Retrofit.Builder()
                .baseUrl("http://apis.baidu.com/baidu_mobile_security/phone_number_service/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttp.client())
                .build()
        return retrofitClient
    }

    fun baiduApi(): BaiduAPI {
        return createRetrofit().create(BaiduAPI::class.java)
    }
}