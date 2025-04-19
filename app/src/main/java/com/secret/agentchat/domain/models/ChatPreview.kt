package com.secret.agentchat.domain.models

data class ChatPreview(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timestamp: String
)