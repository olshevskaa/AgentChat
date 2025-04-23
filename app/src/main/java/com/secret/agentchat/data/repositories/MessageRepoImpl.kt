package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.responses.MessageResponse
import com.secret.agentchat.domain.utils.mappers.toMessage

class MessageRepoImpl(
    private val crypto: CryptoHelper,
    private val
) : MessageRepo {

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

