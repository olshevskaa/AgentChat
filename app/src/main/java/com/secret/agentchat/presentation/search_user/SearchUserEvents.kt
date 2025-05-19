package com.secret.agentchat.presentation.search_user

import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.presentation.chat_list.ChatListEvents

sealed interface SearchUserEvents {
    data class ShowError(val message: UiText): SearchUserEvents
    data class NavigateToChat(val chatId: String, val userId: String) : SearchUserEvents
}