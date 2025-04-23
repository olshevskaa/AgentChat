package com.secret.agentchat.data.api

import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.domain.responses.MessageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocketListener(
    private val cryptoHelper: CryptoHelper
) : WebSocketListener() {

    private val _messages = MutableStateFlow<List<MessageResponse>>(emptyList())
    val messages: StateFlow<List<MessageResponse>> = _messages.asStateFlow()

    override fun onMessage(webSocket: WebSocket, text: String) {
        val message = Json.decodeFromString<MessageResponse>(text)
        _messages.update { currentMessages ->
            currentMessages + message
        }
    }
}