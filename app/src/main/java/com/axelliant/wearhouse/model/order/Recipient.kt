package com.axelliant.wearhouse.model.order

data class Recipient(
    var location: Location?=null,
    val user: User?=null
)