package com.secret.agentchat.domain.responses

data class MessageResponse(
    val _id: String,
    val sender: SenderInfo,
    val recipient: String,
    val encryptedMessage: String,
    val encryptedAESKey: String,
    val signature: String,
    val createdAt: String,
    val expiresAt: String?
)

data class SenderInfo(
    val _id: String,
    val username: String
)