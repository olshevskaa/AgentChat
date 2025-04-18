package com.secret.agentchat.domain.responses

data class AuthResponse(
    val userId: String,
    val token: String,
    val publicKey: String? = null // null for register if not returned
)