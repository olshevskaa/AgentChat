package com.secret.agentchat.domain.utils.mappers

import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.responses.MessageResponse

fun MessageResponse.toMessage(decryptedText: String) = Message(
    chatId = chatId,
    senderId = sender,
    text = decryptedText,
    timestamp = createdAt,
)