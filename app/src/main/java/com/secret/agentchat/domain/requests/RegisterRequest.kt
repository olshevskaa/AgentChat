package com.secret.agentchat.domain.requests

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val publicKey: String
)
