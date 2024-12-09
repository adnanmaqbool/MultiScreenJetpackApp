package com.axelliant.wearhouse.model.order

data class Location(
    var address: String?="279 Westheimer Rd. Santa Ana",
    val city: String?=null,
    val country: String?=null,
    val latitude: String?=null,
    val longitude: String?=null,
    val name: String?=null,
    val state: String?=null,
    val zipcode: String?=null
)