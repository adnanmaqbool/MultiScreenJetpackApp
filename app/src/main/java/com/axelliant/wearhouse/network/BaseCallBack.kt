package com.axelliant.wearhouse.network

import com.axelliant.wearhouse.constants.AppConst.observableCode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseCallBack<T>(private val call: Call<T>) : Callback<T> {
    abstract fun onFinalSuccess(call: Call<T>, response: Response<T>)

    abstract fun onFinalFailure(
        errorString: String? = null
    )

    override fun onResponse(call: Call<T>, response: Response<T>) {
        onResponseValidator(call, response)

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFinalFailure(getErrorFromThrowable(t))
    }


    private fun onResponseValidator(call: Call<T>, response: Response<T>) {
        observableCode.set(response.code())
        if (response.isSuccessful) {
            onFinalSuccess(call, response)
        } else if (response.errorBody() != null) {

            var apiErrorString: String? = response.message()
            try {

                val type: Type = object : TypeToken<ApiError>() {}.type
                val jsonString = response.errorBody()?.string()
                val apiError = Gson().fromJson<ApiError>(jsonString, type)
                apiErrorString = apiError.meta_data?.message
            } catch (e: Exception) {

            }
            if (apiErrorString != null)
                onFinalFailure(apiErrorString)
            else if (response.code() == 500) {
                onFinalFailure(ErrorMessages.InternalServerError500.errorString)
            } else if (response.code() == 400) {
                onFinalFailure(ErrorMessages.BadRequest400.errorString)
            } else if (response.code() == 404) {
                onFinalFailure(ErrorMessages.NotFound404.errorString)
            } else if (response.code() == 401) {
                onFinalFailure(ErrorMessages.SessionExpired401.errorString)
            } else if (response.code() == 422) {
                onFinalFailure(response.message().toString())
            } else {
                onFinalFailure(response.message().toString())
            }

        } else {
            onFinalFailure(errorString = response.toString())
        }

    }


    private fun getErrorFromThrowable(
        t: Throwable
    ): String {
        return when (t) {
            is UnknownHostException, is SocketException -> {
                ErrorMessages.NoInternetError.errorString
            }

            is UnknownHostException -> {
                ErrorMessages.SocketException.errorString
            }

            is SocketTimeoutException -> {
                ErrorMessages.SocketTimeout.errorString
            }

            else -> {
                ErrorMessages.UnknownError.errorString
            }
        }
    }
}