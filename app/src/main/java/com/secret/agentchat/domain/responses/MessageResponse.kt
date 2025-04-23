package com.secret.agentchat.domain.responses

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val id: String,
    val chatId: String,
    val sender: String,
    val recipient: String,
    val encryptedMessage: String,
    val encryptedAESKey: String,
    val signature: String,
    val ephemeral: Boolean,
    val createdAt: String
)