package com.axelliant.wearhouse.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.datastore.SessionManager
import kotlinx.coroutines.delay

/**
 * Splash screen composable that shows an image and navigates to either the home
 * or login screen based on the user's session state.
 *
 * @param navigateToLoginAction Function to navigate to the login screen.
 * @param navigateToHomeAction Function to navigate to the home screen.
 */
@Composable
fun SplashScreen(
    navigateToLoginAction: () -> Unit,
    navigateToHomeAction: () -> Unit
) {
    // Initialize SessionManager to check login state
    val sessionManager = SessionManager(context = LocalContext.current)

    // Effect to delay the splash screen and decide the navigation action
    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds

        // Navigate based on login status
        if (sessionManager.checkLogin()) {
            navigateToHomeAction()
        } else {
            navigateToLoginAction()
        }
    }

    // Render the splash screen content
    SplashContent()
}

/**
 * Composable function to render the splash screen visuals.
 */
@Composable
private fun SplashContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Load and display the splash background image
        val splashBackground: Painter = painterResource(id = R.drawable.splash_bg)
        Image(
            painter = splashBackground,
            contentDescription = "Splash Screen Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * Preview for the splash screen to visualize it in the Compose preview.
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashContent()
}
