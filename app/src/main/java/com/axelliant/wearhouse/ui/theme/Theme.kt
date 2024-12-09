package com.axelliant.wearhouse.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.axelliant.wearhouse.MainActivity

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    )

private val LightColorScheme = lightColorScheme(
    primary = AppColor, // button color
    onPrimary = WhiteColor, // text color on button

    /* Other default colors to override

    secondary = PurpleGrey40,
    tertiary = Pink40


    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WearHouseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    activity: Activity = LocalContext.current as MainActivity,
            content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val window = calculateWindowSizeClass(activity = activity)
    val configuration = LocalConfiguration.current

    var appDimens = CompactSmallDimen
    var typography = CompactSmallTypography

    Log.d("TagIs->","Class ${window.widthSizeClass} Dimens ${configuration.screenWidthDp }")

    when (window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {

            if (configuration.screenWidthDp <= 360) {
                appDimens = CompactSmallDimen
                typography = CompactSmallTypography

            } else if (configuration.screenWidthDp < 599) {
                appDimens = CompactMediumDimen
                typography = CompactMediumTypography

            } else {
                appDimens = CompactDimen
                typography = CompactTypography

            }

        }

        WindowWidthSizeClass.Medium -> {
            appDimens = MediumDimen
            typography = MediumTypography

        }

        WindowWidthSizeClass.Expanded -> {

            appDimens = ExpandedDimen
            typography = ExpandedTypography

        }

    }

    AppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }




}

val MaterialTheme.dimens
 @Composable
 get() = LocalAppDimens.current