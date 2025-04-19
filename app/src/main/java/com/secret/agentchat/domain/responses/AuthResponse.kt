package com.secret.agentchat.domain.responses

data class LoginResponse(
    val userId: String,
    val token: String,
    val publicKey: String
)

data class RegisterResponse(
    val userId: String,
    val token: String
)