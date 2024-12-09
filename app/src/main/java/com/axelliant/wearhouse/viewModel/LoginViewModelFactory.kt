package com.axelliant.wearhouse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.repos.LoginRepo

class LoginViewModelFactory(private val sessionManager: SessionManager, private val loginRepo: LoginRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sessionManager,loginRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}