package com.secret.agentchat.data.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketClient(
    private val url: String,
) {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect(listener: WebSocketListener) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Goodbye!")
    }

}