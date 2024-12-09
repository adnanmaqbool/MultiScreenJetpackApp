package com.axelliant.wearhouse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.repos.LoginRepo
import com.axelliant.wearhouse.repos.OrderRepo

class OrderViewModelFactory(private val sessionManager: SessionManager, private val loginRepo: OrderRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(sessionManager,loginRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}