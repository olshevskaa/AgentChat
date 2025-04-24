package com.secret.agentchat.data.api

import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.responses.MessageResponse
import com.secret.agentchat.domain.utils.mappers.toMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocketListener(
    private val cryptoHelper: CryptoHelper
): WebSocketListener() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    override fun onMessage(webSocket: WebSocket, text: String) {
        val messageResponse = Json.decodeFromString<MessageResponse>(text)
        val decMessage = cryptoHelper.decryptMessage(messageResponse)
        val message = messageResponse.toMessage(decMessage.toString())
        _messages.update { currentMessages ->
            currentMessages + message
        }
    }
}