package com.axelliant.wearhouse.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.axelliant.wearhouse.ui.theme.dimens

@Composable
fun ProfileScreen() {
    ProfileRendering ()
}

@Composable
private fun ProfileRendering() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .width(MaterialTheme.dimens.d100)
                .height(MaterialTheme.dimens.d100)
                .background(
                    Color.Green
                )
                .clickable {
                }
        ) {

            Text(text = "Profile Screen")

        }

    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileRendering()
}