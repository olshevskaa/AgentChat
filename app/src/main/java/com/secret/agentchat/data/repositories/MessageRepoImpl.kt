package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.responses.MessageResponse
import com.secret.agentchat.domain.utils.toMessage
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

class MessageRepoImpl(
    private val api: ApiService,
    val crypto: CryptoHelper
) : MessageRepo {

    override suspend fun getMessages(chatId: String): List<Message?> {
        try {
            val response = api.getMessages(chatId)
            if (!response.isSuccessful) return emptyList()
            response.body()?.let { messages ->
                return messages.map {
                    val decryptedMessage = crypto.decryptMessage(it)
                    it.toMessage(decryptedMessage.toString())
                }
            } ?: return emptyList()
        }catch(e: Exception) {
            return emptyList()
        }
    }

    override suspend fun sendMessage(params: SendMessageParams): MessageResponse? {
        try {
            val request = crypto.encryptMessage(params)
            request?.let {
                val response = api.sendMessage(request)
                if (!response.isSuccessful) return null
                return response.body()
            } ?: return null
        }catch(e: Exception){
            return null
        }
    }

}

