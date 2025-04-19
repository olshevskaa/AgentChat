package com.secret.agentchat.domain.requests

data class CreateChatRequest(
    val userA: String,
    val userB: String
)