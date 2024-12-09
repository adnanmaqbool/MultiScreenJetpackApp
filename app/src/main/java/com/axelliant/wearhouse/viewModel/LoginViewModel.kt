package com.axelliant.wearhouse.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axelliant.wearhouse.constants.AppConst.TOKEN
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.model.login.LoginRequest
import com.axelliant.wearhouse.model.login.UserLoginResponse
import com.axelliant.wearhouse.network.Event
import com.axelliant.wearhouse.repos.LoginRepo
import com.axelliant.wearhouse.validation.Validator.isPasswordValid
import com.axelliant.wearhouse.validation.Validator.isValidEmail

class LoginViewModel(private val sessionManager: SessionManager, private val loginRepo: LoginRepo) :
    ViewModel() {

    var emailController = mutableStateOf(sessionManager.getRememberUserName())
    var passwordController = mutableStateOf(sessionManager.getRememberPassword())
    var rememberController = mutableStateOf(sessionManager.getRememberMe())

    /*   var emailErrorMessage = mutableStateOf("Invalid email address")
       var passErrorMessage = mutableStateOf("Invalid password")*/

    var isEmailValid = mutableStateOf(true)
    var isPasswordValid = mutableStateOf(true)

    private val isLoading: MutableLiveData<Event<Boolean>> by lazy { MutableLiveData<Event<Boolean>>() }
    fun getIsLoading(): LiveData<Event<Boolean>> = isLoading

    val userLoginResponse: MutableLiveData<Event<UserLoginResponse?>> by lazy { MutableLiveData<Event<UserLoginResponse?>>() }

//    val passErrorMessage = remember { mutableStateOf("") }

    fun saveLoginSession(accessToken: String) {

        TOKEN = accessToken

        if (rememberController.value) {

            sessionManager.createLoginSession(
                username = emailController.value,
                userPass = passwordController.value,
                accessToken = accessToken,
                lastRemember = rememberController.value
            )
        } else {
            sessionManager.createLoginSession(
                username = "",
                userPass = "",
                accessToken = accessToken,
                lastRemember = rememberController.value
            )
        }


    }

    fun handleLoginValidation(): Boolean {

        if (isValidEmail(emailController.value) && isPasswordValid(passwordController.value)) {
            // save this into the share preference


            isEmailValid.value = true
            isPasswordValid.value = true

            getUserLogin()

            return true
        } else if (isValidEmail(emailController.value)) {
            isEmailValid.value = true

            return false
        } else if (isPasswordValid(passwordController.value)) {
            isPasswordValid.value = true
            return false
        } else {
            isEmailValid.value = false
            isPasswordValid.value = false

            return false
        }

    }

    private fun getUserLogin() {

        val userLoginRequest = LoginRequest().apply {

            this.email = emailController.value
            this.password = passwordController.value
        }
        isLoading.value = Event(true)
        loginRepo.userLoginApiCall(userLoginRequest)
            .observeForever { data ->

                // Always hide loading on response
                isLoading.value = Event(false)

                // If the response is null or has issues, handle gracefully
                userLoginResponse.value = Event(data?.apply {
                    // Set success message only when the response is successful
                    if (data?.token != null) {

                        this.success = true
                        this.message = "Success"

                    } else {

                        this.success = false
                        this.message = "Failed: ${this?.message ?: "Unknown error"}"

                    }

                })
            }
    }


    fun getSessionManager(): SessionManager {
        return sessionManager
    }


}