package com.ridhaaf.techtopia.feature.presentation.profile

sealed class ProfileEvent {
    data object Refresh : ProfileEvent()
    data object SignOut : ProfileEvent()
}
