package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.repositories.MessageRepo
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

class MessageRepoImpl(
    private val api: ApiService,
    private val sharedPref: SharedPref,
    val crypto: CryptoHelper
) : MessageRepo {

    override suspend fun getMessages(token: String, userId: String): List<Message> {
        try {
            val token = sharedPref.getToken().filter { it.isNotEmpty() }.first()
            val response = api.getMessages("Bearer $token")
            if (!response.isSuccessful) throw Exception("Failed to get messages: ${response.message()}")
            return crypto.decryptMessages(response.body()?: emptyList())
        }catch(e: Exception) {
            return emptyList()
        }
    }

    override suspend fun sendMessage(params: SendMessageParams): Any? {
        try {
            val token = sharedPref.getToken().filter { it.isNotEmpty() }.first()
            val request = crypto.encryptMessage(params)
            request?.let {
                val response = api.sendMessage("Bearer $token", request)
                if (!response.isSuccessful) throw Exception("Failed to send message: ${response.message()}")
                return response.body() ?: Unit
            } ?: return null
        }catch(e: Exception){
            return null
        }
    }

}

