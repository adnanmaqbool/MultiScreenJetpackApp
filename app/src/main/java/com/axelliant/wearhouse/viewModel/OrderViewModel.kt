package com.axelliant.wearhouse.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.model.order.Location
import com.axelliant.wearhouse.model.order.OrderListDetail
import com.axelliant.wearhouse.model.order.OrderListModel
import com.axelliant.wearhouse.model.order.Recipient
import com.axelliant.wearhouse.model.order.Shipper
import com.axelliant.wearhouse.network.Event
import com.axelliant.wearhouse.repos.OrderRepo

/*
class OrderViewModel(private val sessionManager: SessionManager, private val orderRepo: OrderRepo) : ViewModel() {

    val orderList:ArrayList<OrderListDetail> = arrayListOf()

    fun getOrderList() {
        // Add 5 random OrderListDetail objects
        for (i in 1..5) {
            orderList.add(OrderListDetail().apply {
                this.inBound = (i % 2 == 0)  // Alternate between true and false
                this.shipping_time = (System.currentTimeMillis() + i * 1000)
                this.receiving_time = (System.currentTimeMillis() - i * 1000)
                this.title = "Monitor ${randomString(3)}-${randomInt(1000, 9999)}"
                this.recipient = Recipient().apply {
                    location = Location().apply {
                        this.address = randomAddress()
                    }
                }
                this.shipper = Shipper().apply {
                    location = Location().apply {
                        this.address = randomAddress()
                    }
                }
            })
        }
    }

    // Utility function for random address
    fun randomAddress(): String {
        val streets = listOf("Main St.", "Pine Ave.", "Elm Dr.", "Oak Blvd.", "Maple Ln.")
        val cities = listOf("Santa Ana", "Los Angeles", "New York", "San Francisco", "Chicago")
        val numbers = (100..999).random()
        return "$numbers ${streets.random()} ${cities.random()}"
    }

    // Utility function for generating a random string
    fun randomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    // Utility function for generating a random integer within a range
    fun randomInt(min: Int, max: Int): Int {
        return (min..max).random()
    }

}*/

class OrderViewModel(private val sessionManager: SessionManager, private val orderRepo: OrderRepo) : ViewModel() {

    val orderList: ArrayList<OrderListDetail> = arrayListOf()
    val filteredOrderList = mutableStateOf<List<OrderListDetail>>(emptyList())

    fun getOrderList() {
        // Add 5 random OrderListDetail objects
        for (i in 1..5) {
            orderList.add(OrderListDetail().apply {
                this.inBound = (i % 2 == 0)  // Alternate between true and false
                this.shipping_time = (System.currentTimeMillis() + i * 1000)
                this.receiving_time = (System.currentTimeMillis() - i * 1000)
                this.title = "Monitor ${randomString(3)}-${randomInt(1000, 9999)}"
                this.recipient = Recipient().apply {
                    location = Location().apply {
                        this.address = randomAddress()
                    }
                }
                this.shipper = Shipper().apply {
                    location = Location().apply {
                        this.address = randomAddress()
                    }
                }
            })
        }
        // Initially, show all items
        filteredOrderList.value = orderList
    }

    // Function to filter orders based on inBound status
    fun filterOrders(isInBound: Boolean) {
        filteredOrderList.value = orderList.filter { it.inBound == isInBound }
    }

    // Utility functions for random data generation
    fun randomAddress(): String {
        val streets = listOf("Main St.", "Pine Ave.", "Elm Dr.", "Oak Blvd.", "Maple Ln.")
        val cities = listOf("Santa Ana", "Los Angeles", "New York", "San Francisco", "Chicago")
        val numbers = (100..999).random()
        return "$numbers ${streets.random()} ${cities.random()}"
    }

    fun randomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (1..length).map { chars.random() }.joinToString("")
    }

    fun randomInt(min: Int, max: Int): Int {
        return (min..max).random()
    }
}

