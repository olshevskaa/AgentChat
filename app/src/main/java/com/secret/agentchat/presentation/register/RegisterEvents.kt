package com.secret.agentchat.presentation.register

import com.secret.agentchat.core.presentation.UiText

sealed interface RegisterEvents {
    data object Success: RegisterEvents
    data class Failure(val message: UiText): RegisterEvents
}