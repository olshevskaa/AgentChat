package com.secret.agentchat.domain.models

data class Message(
    val senderId: String,
    val recipientId: String,
    val text: String,
    val timestamp: String,
    val signature: String
)