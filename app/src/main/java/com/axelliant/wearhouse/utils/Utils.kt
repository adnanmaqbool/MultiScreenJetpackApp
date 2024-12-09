package com.axelliant.wearhouse.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun unixToDateTime(unix: Long? = null,pattern:String): String {
        if (unix == null)
            return ""

        return try {

//            yyyy-MM-dd hh:mm:ss a
//            June 13, 2024 09:00 AM
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            val date = Date(unix * 1000)

            sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }


    }

}