package com.axelliant.wearhouse.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiHandler {


    private const val BASE_URL = "https://reqres.in/" // Live testing

    private var apiInterface: ApiInterface? = null
    private const val ConnectTimeout = 60L
    private const val ReadTimeout = 60L
    private const val WriteTimeout = 60L

    fun getApiInterface(): ApiInterface? {
        if (apiInterface == null) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(ConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(ReadTimeout, TimeUnit.SECONDS)
                .writeTimeout(WriteTimeout, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            apiInterface = retrofit.create(ApiInterface::class.java)
        }
        return apiInterface
    }
}