package com.axelliant.wearhouse.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.bottomNavigation.BottomNavItem
import com.axelliant.wearhouse.bottomNavigation.BottomNavigationBar
import com.axelliant.wearhouse.constants.AppConst
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.screens.LoginScreen
import com.axelliant.wearhouse.screens.OrderListScreen
import com.axelliant.wearhouse.screens.ProfileScreen
import com.axelliant.wearhouse.screens.ScanScreen
import com.axelliant.wearhouse.screens.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState()

    var isBottomBarVisible by rememberSaveable { mutableStateOf(true) }

    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    if (sessionManager.checkLogin()) {
        val observableCode by AppConst.observableCode.observeAsState()
        // Perform action when observableCode changes to 401
        observableCode?.getContentIfNotHandled().let { code ->
            if (code == 401) {
                navController.navigate(ComposeNavigation.LoginScreen.routeName) {
                    // Clear the back stack up to the root of the graph
                    popUpTo(0) {
                        inclusive = true
                    }
                    // Avoid adding multiple instances of the same destination
                    launchSingleTop = true
                }
            }
        }

    }

    isBottomBarVisible = when (backStackEntry.value?.destination?.route) {
        ComposeNavigation.OrderListScreen.routeName,
        ComposeNavigation.ScanScreen.routeName,
        ComposeNavigation.ProfileScreen.routeName,
        -> true // on these screens bottom bar should be shown
        else -> false // hide other wise
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
/*        Icon(
            painter = painterResource(id = R.drawable.nav_order_list),
            contentDescription = "Order list" )*/
        Scaffold(
            bottomBar = {
                if (isBottomBarVisible) {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavItem(
                                itemName = ComposeNavigation.OrderListScreen.routeName,
                                selectedIcon = painterResource(id = R.drawable.nav_order_list_selected),
                                unSelectedIcon = painterResource(id = R.drawable.nav_order_list_default)

                            ), BottomNavItem(
                                itemName = ComposeNavigation.ScanScreen.routeName,
                                selectedIcon = painterResource(id = R.drawable.nav_scan_selected),
                                unSelectedIcon = painterResource(id = R.drawable.nav_scan_default)
                            ),
                            BottomNavItem(
                                itemName = ComposeNavigation.ProfileScreen.routeName,
                                selectedIcon = painterResource(id = R.drawable.nav_profile_selected),
                                unSelectedIcon = painterResource(id = R.drawable.nav_profile_default)
                            )
                        ), onItemClick = {
                            navController.navigate(it.itemName)
                        },
                        currentItemRoute = backStackEntry.value?.destination?.route.toString()
                    )
                }

            },
            content = { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    // some nested Composable
                    NavHost(
                        navController = navController,
                        startDestination = ComposeNavigation.SplashScreen.routeName
                    ) {

                        composable(route = ComposeNavigation.SplashScreen.routeName) {
                            SplashScreen(navigateToLoginAction = {
                                navController.navigate(ComposeNavigation.LoginScreen.routeName) {
                                    popUpTo(ComposeNavigation.SplashScreen.routeName) {
                                        inclusive = true
                                    }
                                }
                            }, navigateToHomeAction = {
                                navController.navigate(ComposeNavigation.OrderListScreen.routeName) {
                                    popUpTo(ComposeNavigation.SplashScreen.routeName) {
                                        inclusive = true
                                    }
                                }
                            })
                        }
                        composable(route = ComposeNavigation.LoginScreen.routeName) {

                            LoginScreen(loginAction = {
                                navController.navigate(ComposeNavigation.OrderListScreen.routeName) {
                                    popUpTo(ComposeNavigation.LoginScreen.routeName) {
                                        inclusive = true
                                    }
                                }
                            })

                        }

                        composable(route = "${ComposeNavigation.OrderListScreen.routeName}") {
                            OrderListScreen()
                        }

                        composable(route = "${ComposeNavigation.ScanScreen.routeName}") {
                            ScanScreen()
                        }

                        composable(route = "${ComposeNavigation.ProfileScreen.routeName}") {
                            ProfileScreen()
                        }

                    }

                }

            }
        )


    }

}