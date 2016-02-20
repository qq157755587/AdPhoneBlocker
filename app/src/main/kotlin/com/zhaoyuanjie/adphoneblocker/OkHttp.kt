package com.zhaoyuanjie.adphoneblocker

import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response

/**
 * Created by zhaoyuanjie on 15/12/26.
 */
object OkHttp {

    fun client(): OkHttpClient {
        val client: OkHttpClient = OkHttpClient();
        client.networkInterceptors().add(Interceptor { chain ->
            val originalRequest: Request = chain!!.request()
            val response: Response
            // 给request增加header
            if (originalRequest.header("apikey") != null) {
                response = chain.proceed(originalRequest)
            } else {
                val newRequest = originalRequest.newBuilder()
                        .header("apikey", Const.API_KEY)
                        .build()
                response = chain.proceed(newRequest)
            }
            response
        })
        return client
    }
}