package com.axelliant.wearhouse.model.order

data class Driver(
    val is_online: Boolean?=false,
    val location: Location?=null,
    val user: User?=null
)