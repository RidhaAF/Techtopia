package com.ridhaaf.techtopia.feature.presentation.profile

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.techtopia.core.presentation.components.ActionButton
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.defaultToast
import com.ridhaaf.techtopia.core.presentation.routes.Routes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.userError
    val context = LocalContext.current

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                viewModel = viewModel,
                state = state,
                navController = navController,
                context = context,
            )
        },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {
            ProfileContent(state = state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    viewModel: ProfileViewModel,
    state: ProfileState,
    navController: NavController?,
    context: Context,
) {
    DefaultTopAppBar(
        title = "Profile",
        actions = {
            SignOutButton(
                viewModel = viewModel,
                state = state,
                navController = navController,
                context = context,
            )
        },
    )
}

@Composable
private fun ProfileContent(state: ProfileState) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        UserSection(state)
    }
}

@Composable
private fun SignOutButton(
    viewModel: ProfileViewModel,
    state: ProfileState,
    navController: NavController?,
    context: Context,
) {
    ActionButton(
        onClick = { viewModel.onEvent(ProfileEvent.SignOut) },
        icon = Icons.AutoMirrored.Rounded.Logout,
        desc = "Sign Out",
    )

    LaunchedEffect(key1 = state.signOutSuccess, key2 = state.signOutError) {
        if (state.signOutSuccess) {
            navController?.navigate(Routes.SIGN_IN) {
                popUpTo(Routes.PROFILE) {
                    inclusive = true
                }
            }
        }

        if (state.signOutError.isNotBlank()) {
            defaultToast(context, state.signOutError)
        }
    }
}

@Composable
private fun UserSection(state: ProfileState) {
    Column {
        UserDisplayName(state)
        UserName(state)
        UserEmail(state)
    }
}

@Composable
private fun UserDisplayName(state: ProfileState) {
    val user = state.userSuccess
    val text = user?.name ?: ""

    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
        ),
    )
}

@Composable
private fun UserName(state: ProfileState) {
    val user = state.userSuccess
    val text = user?.username ?: ""

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
        ),
    )
}

@Composable
private fun UserEmail(state: ProfileState) {
    val user = state.userSuccess
    val text = user?.email ?: ""

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
        ),
    )
}