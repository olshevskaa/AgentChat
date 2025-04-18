package com.secret.agentchat.domain.repositories

import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams

interface MessageRepo {
    suspend fun getMessages(token: String, userId: String): List<Message>
    suspend fun sendMessage(params: SendMessageParams): Any?
}