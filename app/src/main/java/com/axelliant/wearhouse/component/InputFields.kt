package com.axelliant.wearhouse.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.ui.theme.AppColor
import com.axelliant.wearhouse.ui.theme.GreyColor
import com.axelliant.wearhouse.ui.theme.RedColor

@Composable
fun inputField(
    changeAble: MutableState<String>,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onImeAction: () -> Unit,
    isTrailingIcon: Boolean = false,
    hintText: String = stringResource(id = R.string.app_name),
    isError:Boolean=false
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = changeAble.value,
        modifier = modifier,
        isError = isError,
        onValueChange = {
            changeAble.value = it
        },
        label = {
            hintText(
                hintText,
                GreyColor
            )
        }, // this is  floating hint
        //  placeholder = { OutLinedTextFieldComponent.hintText(hintText, Color.Gray) }, // this is non floating hint
        singleLine = true, // Ensure single line to avoid floating hint behavior
        colors = textFieldColors(),
        keyboardOptions = inputType(
            keyboardType,
            imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        trailingIcon = {

            if (isTrailingIcon) {
                val icon = if (isPasswordVisible)  painterResource(id = R.drawable.visibility_off) else painterResource(id = R.drawable.visibility_on)

                Icon(
                    painter = icon,
                    contentDescription = "password icon",
                    modifier = Modifier.clickable {
                        isPasswordVisible = !isPasswordVisible
                    }
                )
            }

        }, visualTransformation = if (isTrailingIcon) {
            if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }

    )


}

@Composable
fun textFieldColors(): TextFieldColors {

    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val invertedUiColor = if (isSystemInDarkTheme())  Color.Black else Color.White
    val labelColor = if (isSystemInDarkTheme())  AppColor else GreyColor



    return TextFieldDefaults.colors(
        focusedIndicatorColor = uiColor,
        unfocusedIndicatorColor = GreyColor,
        cursorColor = uiColor,
        focusedTextColor = uiColor,
        focusedLabelColor = uiColor,
        unfocusedTextColor = uiColor,
        errorLabelColor= RedColor,
        errorTrailingIconColor = RedColor,
        errorPlaceholderColor = RedColor,
        unfocusedLabelColor = labelColor,

        focusedContainerColor = invertedUiColor,
        unfocusedContainerColor = invertedUiColor,
        disabledContainerColor = invertedUiColor,
        errorContainerColor = invertedUiColor,
    )


   /* return TextFieldDefaults.colors(
        focusedIndicatorColor = BlackColor,
        unfocusedIndicatorColor = GreyColor,
        cursorColor = BlackColor,
        focusedTextColor = BlackColor,
        focusedLabelColor = BlackColor,
        unfocusedTextColor = BlackColor,
        errorLabelColor= RedColor,
        errorTrailingIconColor = RedColor,
        errorPlaceholderColor = RedColor,
        unfocusedLabelColor = GreyColor,

        focusedContainerColor = WhiteColor,
        unfocusedContainerColor = WhiteColor,
        disabledContainerColor = WhiteColor,
        errorContainerColor = WhiteColor,
    )*/

}


@Composable
fun outLinedModifier(): Modifier {
    return Modifier
        .fillMaxWidth()

}


@Composable
fun labeledText(labelString: String) {
    Text(text = labelString)
}

@Composable
fun hintText(labelString: String, color: Color = Color.Gray) {
    Text(text = labelString, color = color)
}

@Composable
fun inputType(keyboardType: KeyboardType, imeAction: ImeAction): KeyboardOptions {
    return KeyboardOptions.Default.copy(
        keyboardType = keyboardType,
        imeAction = imeAction
    )
}

