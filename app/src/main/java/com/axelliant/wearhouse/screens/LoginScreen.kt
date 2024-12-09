package com.axelliant.wearhouse.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.component.CustomButton
import com.axelliant.wearhouse.component.HeadingTextView
import com.axelliant.wearhouse.component.MediumTextView
import com.axelliant.wearhouse.component.inputField
import com.axelliant.wearhouse.component.outLinedModifier
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.dialog.LoadingDialog
import com.axelliant.wearhouse.model.login.LoginRequest
import com.axelliant.wearhouse.network.ApiHandler
import com.axelliant.wearhouse.network.ApiInterface
import com.axelliant.wearhouse.repos.LoginRepo
import com.axelliant.wearhouse.ui.theme.AppColor
import com.axelliant.wearhouse.ui.theme.WhiteColor
import com.axelliant.wearhouse.ui.theme.dimens
import com.axelliant.wearhouse.viewModel.LoginViewModel
import com.axelliant.wearhouse.viewModel.LoginViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call

// Main composable function for the Login screen
@Composable
fun LoginScreen(loginAction: () -> Unit) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    // Initialize ViewModel with SessionManager and LoginRepo
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            sessionManager,
            LoginRepo(apiInterface = ApiHandler.getApiInterface()!!)
        )
    )

    loginRendering(loginAction, loginViewModel)

    val isLoading by loginViewModel.getIsLoading().observeAsState()
    if (isLoading?.peekContent() == true)
        LoadingDialog(isLoading = true) // Show loading dialog when true
    else
        LoadingDialog(isLoading = false) // Hide loading dialog when false

    // Observe the login response from the ViewModel
    val loginResponse by loginViewModel.userLoginResponse.observeAsState()
    loginResponse?.getContentIfNotHandled()?.let { response ->

        // Check if login was successful and navigate accordingly
        if (response.success == true) {
            loginViewModel.saveLoginSession(response.token.toString()) // Save login session
            LaunchedEffect(Unit) {
                loginAction() // Perform the login action
            }
        } else {
            // Show error message if login fails
            response.message?.let {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        "error $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

// Function to render the login screen layout
@Composable
private fun loginRendering(homeNavigateAction: () -> Unit, loginViewModel: LoginViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {
        headerLogin() // Display header (background image)
        inputLogin(homeNavigateAction, loginViewModel) // Input fields for email and password
        bottomLogin(homeNavigateAction, loginViewModel) // Login button at the bottom
    }
}

// Function to render the login button at the bottom of the screen
@Composable
private fun bottomLogin(homeNavigateAction: () -> Unit, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(MaterialTheme.dimens.d10),
        contentAlignment = Alignment.BottomCenter
    ) {
        CustomButton(
            onClick = {
                loginCall(loginViewModel) // Call login function when button is clicked
            },
            text = stringResource(R.string.login),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColor,
                contentColor = WhiteColor
            ),
            shape = RoundedCornerShape(MaterialTheme.dimens.d20) // Custom button shape
        )
    }
}

// Function to render the input fields for login
@Composable
private fun inputLogin(homeNavigateAction: () -> Unit, loginViewModel: LoginViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .padding(MaterialTheme.dimens.d10)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadingTextView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.d10),
                textString = stringResource(R.string.welcome)
            )
            MediumTextView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.d10, bottom = MaterialTheme.dimens.d10),
                textString = stringResource(R.string.sign_in_details)
            )
            inputFieldsSection(homeNavigateAction, loginViewModel) // Input fields for email and password
            TitleWithCheckbox(
                isChecked = loginViewModel.rememberController.value,
                onCheckedChange = {
                    loginViewModel.getSessionManager().setRememberMe(it)
                    loginViewModel.rememberController.value = it
                }
            )
        }
    }
}

// Function to check if email is valid
private fun isEmailError(loginViewModel: LoginViewModel): Boolean {
    return !loginViewModel.isEmailValid.value
}

// Function to check if password is valid
private fun isPasswordError(loginViewModel: LoginViewModel): Boolean {
    return !loginViewModel.isPasswordValid.value
}

// Function to render input fields for email and password
@Composable
private fun inputFieldsSection(homeNavigateAction: () -> Unit, loginViewModel: LoginViewModel) {
    val passwordFocusRequester = FocusRequester()
    val context = LocalContext.current

    // Email input field
    inputField(
        changeAble = loginViewModel.emailController,
        isError = isEmailError(loginViewModel),
        modifier = outLinedModifier(),
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next, // Show "Next" button on the keyboard
        onImeAction = {
            // Move focus to the password field
            passwordFocusRequester.requestFocus()
        },
        hintText = stringResource(id = R.string.phone_no_email_id)
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.d20)
    )

    // Password input field
    inputField(
        changeAble = loginViewModel.passwordController,
        isError = isPasswordError(loginViewModel),
        modifier = outLinedModifier().focusRequester(passwordFocusRequester),
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done, // Show "Done" button on the keyboard
        onImeAction = {
            // Handle "Done" action
            loginCall(loginViewModel)
        },
        isTrailingIcon = true,
        hintText = stringResource(id = R.string.password)
    )
}

// Function to render a checkbox with a title
@Composable
fun TitleWithCheckbox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    titleString: String = stringResource(id = R.string.remember_me),
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) } // Toggle checkbox when clicked
    ) {
        val negativeOffset = -MaterialTheme.dimens.d10
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) }, // Explicitly call onCheckedChange when checkbox is clicked
            modifier = Modifier
                .offset(x = negativeOffset)
                .padding(0.dp) // Visualize bounds
        )
        MediumTextView(
            textString = titleString, modifier = Modifier
                .padding(0.dp)
                .offset(x = negativeOffset)
        )
    }
}

// Function to render the header with the background image
@Composable
private fun headerLogin() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.30f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

// Function to make the login API call
private fun loginCall(loginViewModel: LoginViewModel) {

    // Make the API call to login
    loginViewModel.handleLoginValidation()
}
