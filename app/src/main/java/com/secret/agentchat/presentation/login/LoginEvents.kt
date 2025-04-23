package com.secret.agentchat.presentation.login

import com.secret.agentchat.core.presentation.UiText

sealed interface LoginEvents {
    data object Success: LoginEvents
    data class Failure(val message: UiText): LoginEvents
    data object NavigateToSignUp: LoginEvents
}