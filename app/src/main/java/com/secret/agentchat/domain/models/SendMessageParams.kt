package com.secret.agentchat.domain.models

data class SendMessageParams(
    val recipientPublicKey: String,
    val message: String,
    val userId: String,
    val recipientId: String,
)
