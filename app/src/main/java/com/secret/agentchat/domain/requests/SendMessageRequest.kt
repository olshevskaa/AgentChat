package com.secret.agentchat.domain.requests

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val chatId: String,
    val senderId: String,
    val encryptedMessage: String,
    val encryptedAESKey: String,
    val signature: String,
    val ephemeral: Boolean,
    val createdAt: String
)