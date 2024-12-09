package com.axelliant.wearhouse.navigation


sealed class ComposeNavigation(val routeName:String) {

    data object SplashScreen : ComposeNavigation("splash_screen")
    data object LoginScreen : ComposeNavigation("login_screen")
    data object OrderListScreen : ComposeNavigation("order_list_screen")
    data object ScanScreen : ComposeNavigation("scan_screen")
    data object ProfileScreen : ComposeNavigation("profile_screen")

    fun withArgs(vararg args:String):String{

        return buildString {
            append(routeName)
                args.forEach { arg->
                    append("/$arg")
                }
        }
    }




}