package com.axelliant.wearhouse.repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.axelliant.wearhouse.constants.AppConst
import com.axelliant.wearhouse.model.base.BaseApiModel
import com.axelliant.wearhouse.model.base.Meta
import com.axelliant.wearhouse.model.order.OrderListModel
import com.axelliant.wearhouse.network.ApiInterface
import com.axelliant.wearhouse.network.BaseCallBack
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type

class OrderRepo(private var apiInterface: ApiInterface) {


    fun getOrderList(page:Int=1,history:String="False"): MutableLiveData<BaseApiModel<OrderListModel>?> {
        val orderListResponse = MutableLiveData<BaseApiModel<OrderListModel>?>()
        val call = apiInterface.orderListCall("Bearer ${AppConst.TOKEN}",page,history)

        Log.e("HTTP Request", " " + call.request().toString())

        call?.enqueue(object : BaseCallBack<ResponseBody>(call) {
            override fun onFinalSuccess(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                val type: Type = object : TypeToken<BaseApiModel<OrderListModel>>() {}.type
                val jsonString = response.body()?.string()
                val userModel = Gson().fromJson<BaseApiModel<OrderListModel>>(jsonString, type)
                orderListResponse.value = userModel
            }
            override fun onFinalFailure(
                errorString: String?
            ) {
                orderListResponse.value =
                    BaseApiModel(data = OrderListModel(), meta_data = Meta().apply {
                        this.message =errorString.toString()
                        this.success =false})
            }

        })


        return orderListResponse
    }


}