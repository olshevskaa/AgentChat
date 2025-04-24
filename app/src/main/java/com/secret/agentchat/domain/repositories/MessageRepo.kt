package com.secret.agentchat.domain.repositories

import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.responses.MessageResponse

interface MessageRepo {
    suspend fun getMessages(chatId: String): List<Message>
    suspend fun sendMessage(params: SendMessageParams): Boolean
}