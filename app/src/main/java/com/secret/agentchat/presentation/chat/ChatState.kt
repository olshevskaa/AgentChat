package com.secret.agentchat.presentation.chat

import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.User

data class ChatState(
    val myId: String = "",
    val messages: List<Message> = emptyList(),
    val isChatLoading: Boolean = false,
    val chatPartner: User = User.EmptyUser,
    val currentMessage: String = "",
)