package com.secret.agentchat.domain.requests

import org.bson.types.ObjectId

data class RegisterRequest(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val publicKey: String
)
