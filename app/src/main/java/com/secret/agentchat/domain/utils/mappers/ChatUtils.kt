package com.secret.agentchat.domain.utils.mappers

import com.secret.agentchat.domain.models.ChatPreview
import com.secret.agentchat.domain.responses.ChatResponse

fun ChatResponse.toChatPreview(decryptedMessage: String): ChatPreview {
    return ChatPreview(
        id = id,
        name = lastMessage.id,
        lastMessage = decryptedMessage,
        timestamp = lastMessage.createdAt
    )
}