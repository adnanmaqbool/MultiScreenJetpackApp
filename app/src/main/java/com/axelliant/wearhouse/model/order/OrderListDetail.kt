package com.axelliant.wearhouse.model.order

import com.axelliant.wearhouse.model.base.Meta


class OrderListDetail : Meta() {

     val attachment: String? = null
     val attachments: List<Int>? = null
     val barcode: String? = null
     val detail: Any? = null
     val dimensions: String? = null
     val driver: Driver? = null
     val id: Int? = 123
     val is_deleted: Boolean? = false
     val loading_time: Long? = null
     var shipping_time: Long? = 12312312313
     var receiving_time: Long? = 12312312312
     val order_route_points: ArrayList<List<Double>>? = null
     val order_status: Int? = null
     val order_type: Int? = null
     var recipient: Recipient? = null
     var shipper: Shipper? = null
     var title: String? = "Monitor A0C 24G3"
     val updated_at: Int? = null
     val updated_by: Any? = null
     val weight: String? = null
     var inBound: Boolean? = false

}