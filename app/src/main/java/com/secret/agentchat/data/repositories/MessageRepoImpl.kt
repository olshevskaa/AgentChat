package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.data.api.WebSocketClient
import com.secret.agentchat.data.api.formatWebSocketEvent
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.models.WebSocketEvent
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.responses.MessageResponse
import com.secret.agentchat.domain.utils.mappers.toMessage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MessageRepoImpl(
    private val crypto: CryptoHelper,
    private val webClient: WebSocketClient,
    val api: ApiService
) : MessageRepo {
    override suspend fun getMessages(chatId: String): List<Message> {
        try {
            val response = api.getMessages(chatId)
            if(response.isSuccessful){
                response.body()?.let { messages ->
                    return messages.map {
                        val decMessage = crypto.decryptMessage(it)
                        it.toMessage(decMessage.toString())
                    }
                }
            }
            return emptyList()
        }catch(e: Exception){
            return emptyList()
        }
    }

    override suspend fun sendMessage(params: SendMessageParams) : Boolean{
        try {
            val request = crypto.encryptMessage(params)
            request?.let {
                val jsonRequest = Json.encodeToString(request)
                val event = Json.encodeToString(WebSocketEvent("send_message", jsonRequest))
                webClient.send(event)
                return true
            }
            return false
        }catch(e: Exception){
            return false
        }
    }

}

