package com.secret.agentchat.data.api

import com.secret.agentchat.domain.models.WebSocketEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun WebSocketEvent.formatWebSocketEvent() : String{
    return Json.encodeToString(this)
}