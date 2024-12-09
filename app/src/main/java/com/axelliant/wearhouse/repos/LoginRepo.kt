package com.axelliant.wearhouse.repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.axelliant.wearhouse.model.login.LoginRequest
import com.axelliant.wearhouse.model.login.UserLoginResponse
import com.axelliant.wearhouse.network.ApiInterface
import com.axelliant.wearhouse.network.BaseCallBack
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type


class LoginRepo(private var apiInterface: ApiInterface) {
    fun userLoginApiCall(loginRequest: LoginRequest): MutableLiveData<UserLoginResponse?> {
        val userLoginResponse = MutableLiveData<UserLoginResponse?>()
        val call = apiInterface.userLoginCall(loginRequest)
        Log.e("HTTP Request", " " + call?.request().toString())

        call?.enqueue(object : BaseCallBack<ResponseBody>(call) {
            override fun onFinalSuccess(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                val type: Type = object : TypeToken<UserLoginResponse>() {}.type
                val jsonString = response.body()?.string()
                val userModel = Gson().fromJson<UserLoginResponse>(jsonString, type)
                userLoginResponse.value = userModel
            }

            override fun onFinalFailure(
                errorString: String?
            ) {
                userLoginResponse.value =
                    UserLoginResponse().apply {
                        this.success=false
                        this.message = errorString.toString()
                    }
            }

        })


        return userLoginResponse
    }
}
