package com.secret.agentchat.domain.models

data class ChatPreview(
    val id: String,
    val lastMessage: String,
    val timestamp: String,
    val participants: List<String>,
    val name: String = "Unknown User"
)