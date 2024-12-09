package com.axelliant.wearhouse.datastore

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private var pref: SharedPreferences? = null

    // Editor for Shared preferences
    private var editor: SharedPreferences.Editor? = null

    // Context
    private var _context: Context? = null

    // Shared pref mode
    private val PRIVATE_MODE = 0

    private val PREF_NAME = "UserPref"
    private val IS_LOGIN = "IsLoggedIn"
    private val IS_REMEMBER = "IsRememberMe"
    private val isLastRemember = "isLastRemember"
    private val KEY_EMAIL = "email"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"
    private val KEY_TOKEN = "token"


    init {
        _context = context
        pref = _context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref!!.edit()
    }

    fun setRememberMe(checkValue:Boolean=false){
        editor!!.putBoolean(IS_REMEMBER,checkValue)
        editor!!.commit()
    }

    fun getRememberMe():Boolean{
        return pref!!.getBoolean(IS_REMEMBER, false)
    }


    /**
     * Create login session  KEY_ADMINNAME
     */
    fun createLoginSession(username: String?,
                           userPass: String?,
                           accessToken: String?,
                           lastRemember:Boolean=false) {
        // Storing login value as TRUE
        editor!!.putBoolean(IS_LOGIN, true)
        editor!!.putBoolean(isLastRemember, lastRemember)
        // Storing password in pref
        // Storing name in pref
        editor!!.putString(KEY_USERNAME, username)
        editor!!.putString(KEY_PASSWORD, userPass)
        editor!!.putString(KEY_TOKEN, accessToken)
        //        editor.putString(KEY_TYPE, role);
        // commit changes
        editor!!.commit()

    }
    fun getRememberUserName():String{
        return if(isLoggedIn())
            pref!!.getString(KEY_USERNAME, "").toString()
        else
            ""

    }
    fun getRememberPassword():String{
        return if(isLoggedIn())
            pref!!.getString(KEY_PASSWORD, "").toString()
        else
            ""

    }

    fun checkLogin(): Boolean {
        // Check login status
        return isLoggedIn()
    }




    /**
     * Clear session details
     */
    fun logoutUser(): Boolean {
        // Clearing all data from Shared Preferences
        editor!!.clear()
        editor!!.commit()


        // After logout redirect user to Loing Activity
       /* val i = Intent(_context, LoginFragment::class.java)
        // Closing all the Requests
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        // Staring Login Activity
        _context!!.startActivity(i)*/
        // Add new Flag to start new Activity
        return true
    }


    /**
     * Quick check for login
     */
    // Get Login State
    private fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(IS_LOGIN, false)
    }


    fun savePassword(password: String?) {
        editor!!.putString(KEY_PASSWORD, password)
        editor!!.commit()
    }

    fun saveUserEmail(email: String?) {
        editor!!.putString(KEY_EMAIL, email)
        editor!!.commit()
    }


    fun getUserEmail(): String? {
        return pref!!.getString(KEY_EMAIL, null)
    }


    fun getPassword(): String? {
        return pref!!.getString(KEY_PASSWORD, null)
    }

}