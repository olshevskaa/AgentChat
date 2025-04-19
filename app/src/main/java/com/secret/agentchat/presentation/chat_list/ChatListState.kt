package com.secret.agentchat.presentation.chat_list

import com.secret.agentchat.domain.models.ChatPreview

data class ChatListState(
    val chats: List<ChatPreview> = emptyList(),
    val isLoading: Boolean = false,
)