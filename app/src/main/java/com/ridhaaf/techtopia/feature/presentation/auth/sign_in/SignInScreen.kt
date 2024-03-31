package com.ridhaaf.techtopia.feature.presentation.auth.sign_in

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
import com.ridhaaf.techtopia.core.presentation.routes.Routes
import com.ridhaaf.techtopia.core.utils.redirectFromAuth

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.signInError
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.isAuth()) {
        if (viewModel.isAuth()) {
            redirectFromAuth(navController)
        }
    }

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
            SignInContent(
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
        title = "Sign In",
        isActionsEnabled = false,
    )
}

@Composable
private fun SignInContent(
    viewModel: SignInViewModel,
    state: SignInState,
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
        EmailTextField(viewModel)
        PasswordTextField(viewModel)
        SignInButton(
            viewModel = viewModel,
            state = state,
            navController = navController,
        )
        RedirectToSignIn(navController)
    }
}

@Composable
private fun EmailTextField(viewModel: SignInViewModel) {
    DefaultTextField(
        value = viewModel.email,
        onValueChange = { viewModel.onEvent(SignInEvent.Email(it)) },
        placeholder = "Email",
    )
}

@Composable
private fun PasswordTextField(viewModel: SignInViewModel) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    DefaultTextField(
        value = viewModel.password,
        onValueChange = { viewModel.onEvent(SignInEvent.Password(it)) },
        placeholder = "Password",
        isObscure = !passwordVisibility,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility },
            ) {
                val icon = if (passwordVisibility) Icons.Rounded.VisibilityOff
                else Icons.Rounded.Visibility
                val contentDescription = if (passwordVisibility) "Hide password"
                else "Show password"

                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                )
            }
        },
    )
}

@Composable
private fun SignInButton(
    viewModel: SignInViewModel,
    state: SignInState,
    navController: NavController?,
) {
    val text = if (state.isSignInLoading) "Signing in..." else "Sign in"

    DefaultButton(
        onClick = { viewModel.onEvent(SignInEvent.SignIn) },
        child = { Text(text) },
    )

    if (state.signInSuccess != null) {
        redirectFromAuth(navController)
    }
}

@Composable
private fun RedirectToSignIn(navController: NavController?) {
    RedirectToAuth(
        onClick = { navController?.navigate(Routes.SIGN_UP) },
        title = "Don't have an account? Sign Up",
    )
}