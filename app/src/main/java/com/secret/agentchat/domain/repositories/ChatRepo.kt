package com.secret.agentchat.domain.repositories

import com.secret.agentchat.domain.models.ChatPreview
import com.secret.agentchat.domain.requests.CreateChatRequest
import com.secret.agentchat.domain.responses.ChatResponse

interface ChatRepo {
    suspend fun getChats() : List<ChatPreview>?
    suspend fun getChatByParticipants(recipiendId: String) : ChatPreview?
    suspend fun createChat(request: CreateChatRequest) : ChatResponse?
}