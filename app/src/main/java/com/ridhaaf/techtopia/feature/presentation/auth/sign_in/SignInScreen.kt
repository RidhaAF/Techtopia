package com.ridhaaf.techtopia.feature.presentation.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.DefaultButton
import com.ridhaaf.techtopia.core.presentation.components.DefaultTextField
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.routes.Routes

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
) {
    Scaffold(
        topBar = { TopBar() },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            SignInContent(navController)
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
private fun SignInContent(navController: NavController?) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        EmailTextField()
        PasswordTextField()
        SignInButton(navController)
        RedirectToSignUp(navController)
    }
}

@Composable
private fun EmailTextField() {
    DefaultTextField(
        value = "",
        onValueChange = {},
        placeholder = "Email",
    )
}

@Composable
private fun PasswordTextField() {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    DefaultTextField(
        value = "",
        onValueChange = { },
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
    navController: NavController?,
) {
    DefaultButton(
        onClick = { },
        child = { Text("Sign in") },
    )
}

@Composable
private fun RedirectToSignUp(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        TextButton(
            onClick = { navController?.navigate(Routes.SIGN_UP) },
            modifier = Modifier.wrapContentSize(align = Alignment.Center),
        ) {
            Text(
                text = "Don't have an account? Sign Up",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary,
                ),
            )
        }
    }
}