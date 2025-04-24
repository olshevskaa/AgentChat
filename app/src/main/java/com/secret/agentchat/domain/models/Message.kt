package com.secret.agentchat.domain.models

data class Message(
    val chatId: String,
    val messageId: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
)