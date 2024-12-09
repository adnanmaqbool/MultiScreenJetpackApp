package com.axelliant.wearhouse.model.order

import com.axelliant.wearhouse.model.base.Meta

class OrderListModel: Meta(){
    val count: Int?=null
    val current_page: Int?=null
    val results: List<OrderListDetail>?=null
}