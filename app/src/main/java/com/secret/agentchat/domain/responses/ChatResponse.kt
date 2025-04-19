package com.secret.agentchat.domain.responses

data class ChatResponse(
    val id: String,
    val participants: List<String>,
    val lastMessage: MessageResponse
)