package com.ridhaaf.techtopia.feature.presentation.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.DefaultButton
import com.ridhaaf.techtopia.core.presentation.components.DefaultTextField
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.RedirectToAuth
import com.ridhaaf.techtopia.core.presentation.components.defaultToast
import com.ridhaaf.techtopia.core.utils.redirectFromAuth
import java.util.Locale

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.signUpError
    val context = LocalContext.current

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }

    Scaffold(
        topBar = { TopBar() },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            SignUpContent(
                viewModel = viewModel,
                state = state,
                navController = navController,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    DefaultTopAppBar(
        title = "Sign Up",
        isActionsEnabled = false,
    )
}

@Composable
private fun SignUpContent(
    viewModel: SignUpViewModel,
    state: SignUpState,
    navController: NavController?,
) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        NameTextField(viewModel)
        UsernameTextField(viewModel)
        EmailTextField(viewModel)
        PasswordTextField(viewModel)
        ConfirmPasswordTextField(viewModel)
        SignUpButton(
            viewModel = viewModel,
            state = state,
            navController = navController,
        )
        RedirectToSignIn(navController)
    }
}

@Composable
private fun NameTextField(viewModel: SignUpViewModel) {
    DefaultTextField(
        value = viewModel.name,
        onValueChange = { viewModel.onEvent(SignUpEvent.Name(it)) },
        placeholder = "Name",
    )
}

@Composable
private fun UsernameTextField(viewModel: SignUpViewModel) {
    DefaultTextField(
        value = viewModel.username,
        onValueChange = { viewModel.onEvent(SignUpEvent.Username(it)) },
        placeholder = "Username",
    )
}

@Composable
private fun EmailTextField(viewModel: SignUpViewModel) {
    DefaultTextField(
        value = viewModel.email,
        onValueChange = { viewModel.onEvent(SignUpEvent.Email(it)) },
        placeholder = "Email",
    )
}

@Composable
private fun PasswordTextFieldContent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    DefaultTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        isObscure = !passwordVisibility,
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                },
            ) {
                val icon = if (passwordVisibility) Icons.Rounded.VisibilityOff
                else Icons.Rounded.Visibility
                val contentDescription =
                    if (passwordVisibility) "Hide ${placeholder.lowercase(Locale.getDefault())}"
                    else "Show ${placeholder.lowercase(Locale.getDefault())}"

                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                )
            }
        },
    )
}

@Composable
fun PasswordTextField(viewModel: SignUpViewModel) {
    PasswordTextFieldContent(
        value = viewModel.password,
        onValueChange = { viewModel.onEvent(SignUpEvent.Password(it)) },
        placeholder = "Password",
    )
}

@Composable
fun ConfirmPasswordTextField(viewModel: SignUpViewModel) {
    PasswordTextFieldContent(
        value = viewModel.confirmPassword,
        onValueChange = { viewModel.onEvent(SignUpEvent.ConfirmPassword(it)) },
        placeholder = "Confirm Password",
    )
}

@Composable
private fun SignUpButton(
    viewModel: SignUpViewModel,
    state: SignUpState,
    navController: NavController?,
) {
    val text = if (state.isSignUpLoading) "Signing up..." else "Sign up"

    DefaultButton(
        onClick = { viewModel.onEvent(SignUpEvent.SignUp) },
        child = { Text(text) },
    )

    if (state.signUpSuccess != null) {
        redirectFromAuth(navController)
    }
}

@Composable
private fun RedirectToSignIn(navController: NavController?) {
    RedirectToAuth(
        onClick = { navController?.popBackStack() },
        title = "Already have an account? Sign In",
    )
}