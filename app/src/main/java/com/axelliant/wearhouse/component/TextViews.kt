package com.axelliant.wearhouse.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.axelliant.wearhouse.ui.theme.*

@Composable
fun HeadingTextView(
    modifier: Modifier = Modifier,
    textString: String,
    color: Color = AppColor,
    fontSize:TextUnit?=null

) {

    Text(
        modifier = modifier,
        text = textString,
        style = MaterialTheme.typography.headlineLarge,
        color = color,
        fontSize  = fontSize ?: MaterialTheme.typography.headlineLarge.fontSize

    )

}

@Composable
fun MediumTextView(
    modifier: Modifier = Modifier,
    textString: String,
    color: Color = BlackColor
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black


    Text(
        modifier = modifier,
        text = textString,
        style = MaterialTheme.typography.titleMedium,
        color = uiColor

    )

}

@Composable
fun SmallTextView(
    modifier: Modifier = Modifier,
    textString: String,
    color: Color = GreyColor
) {

    Text(
        modifier = modifier,
        text = textString,
        style = MaterialTheme.typography.titleMedium,
        color = color
    )

}
