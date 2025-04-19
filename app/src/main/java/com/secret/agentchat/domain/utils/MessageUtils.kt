package com.secret.agentchat.domain.utils

import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.responses.MessageResponse

fun MessageResponse.toMessage(decryptedText: String) = Message(
    chatId = chatId,
    messageId = id,
    senderId = sender,
    text = decryptedText,
    timestamp = createdAt,
    signature = signature
)