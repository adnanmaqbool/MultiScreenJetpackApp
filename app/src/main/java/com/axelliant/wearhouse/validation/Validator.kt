package com.axelliant.wearhouse.validation

object Validator {

    fun isPasswordValid(password: String): Boolean {
        val emailRegex = "^(?=.*[0-9])".toRegex()
//    return emailRegex.matches(email)

        return password.length > 3
    }


    fun isValidEmail(email: String): Boolean {
        val emailRegex =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}\$".toRegex()
        return emailRegex.matches(email)
    }

}