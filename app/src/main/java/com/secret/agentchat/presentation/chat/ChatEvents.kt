package com.secret.agentchat.presentation.chat

import com.secret.agentchat.core.presentation.UiText

sealed interface ChatEvents {
    data object Success: ChatEvents
    data class Failure(val message: UiText): ChatEvents
}