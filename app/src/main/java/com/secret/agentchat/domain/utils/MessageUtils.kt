package com.secret.agentchat.domain.utils

import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.responses.MessageResponse

fun MessageResponse.toMessage(decryptedText: String) = Message(
    senderId = sender._id,
    recipientId = recipient,
    text = decryptedText,
    timestamp = createdAt,
    signature = signature
)