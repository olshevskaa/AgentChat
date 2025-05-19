package com.secret.agentchat.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketEvent(
    val type: String,
    val data : String
)