package com.axelliant.wearhouse.model.base

import com.google.gson.annotations.SerializedName

data class BaseApiModel<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("meta_data")
    val meta_data: Meta?,
)

data class BaseModel<T>(
    // Define your BaseModel properties here
    @SerializedName("data")
    val data: T?
)

