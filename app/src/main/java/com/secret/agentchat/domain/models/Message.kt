package com.secret.agentchat.domain.models

import java.time.Instant


data class Message(
    val chatId: String,
    val senderId: String,
    val text: String,
    val timestamp: String = Instant.now().toEpochMilli().toString(),
)