package com.secret.agentchat.presentation.chat_list

import com.secret.agentchat.core.presentation.UiText

sealed interface ChatListEvents {
    data class Failure(val message: UiText) : ChatListEvents
    data class NavigateToChat(val chatId: String, val userId: String) : ChatListEvents
}