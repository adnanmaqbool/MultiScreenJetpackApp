package com.axelliant.wearhouse.network

import com.axelliant.wearhouse.model.login.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("api/register")
    fun userLoginCall(@Body loginRequest: LoginRequest): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("order/all/")
    fun orderListCall(@Header("Authorization") auth: String?, @Query("page") page:Int=1, @Query("history") history:String="False"): Call<ResponseBody>


}