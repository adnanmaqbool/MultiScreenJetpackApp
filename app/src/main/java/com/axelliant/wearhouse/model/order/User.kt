package com.axelliant.wearhouse.model.order

data class User(
    val address: String?=null,
    val email: String?=null,
    val full_name: String?=null,
    val phone: String?=null,
    val user_status: Boolean?=false
)