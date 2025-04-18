package com.secret.agentchat.domain.requests

data class SendMessageRequest(
    val recipientId: String,
    val encryptedMessage: String,    // AES-encrypted message, Base64 encoded
    val encryptedAESKey: String,     // AES key encrypted with recipient's public key, Base64 encoded
    val signature: String,           // digital signature of encryptedMessage, Base64 encoded
    val expiresAt: String? = null    // optional ISO timestamp
)