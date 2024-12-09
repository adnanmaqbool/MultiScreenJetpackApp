package com.axelliant.wearhouse.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.axelliant.wearhouse.ui.theme.dimens

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    colors:ButtonColors,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(MaterialTheme.dimens.d15) // Default shape
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.d40), // Default height
        shape = shape,
        colors = colors
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
