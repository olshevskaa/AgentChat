package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.ChatPreview
import com.secret.agentchat.domain.repositories.ChatRepo
import com.secret.agentchat.domain.requests.CreateChatRequest
import com.secret.agentchat.domain.responses.ChatResponse
import com.secret.agentchat.domain.utils.mappers.toChatPreview
import kotlinx.coroutines.flow.first

class ChatRepoImpl(
    private val api: ApiService,
    private val sharedPref: SharedPref,
    private val cryptoHelper: CryptoHelper
) : ChatRepo {
    override suspend fun getChats(): List<ChatPreview>? {
        try {
            val userId = sharedPref.getUserId().first()
            val response = api.getChats(userId.toString())
            if(!response.isSuccessful) return null
            response.body()?.let { chatList ->
                return chatList.map { chat ->
                    val decryptedMessage = cryptoHelper.decryptMessage(chat.lastMessage)
                    chat.toChatPreview(decryptedMessage.toString())
                }
            } ?: return null
        }catch(e: Exception) {
            return null
        }
    }

    override suspend fun createChat(request: CreateChatRequest): ChatResponse? {
        return try {
            val response = api.createChat(request)
            if(!response.isSuccessful) return null
            return response.body()
        }catch(e: Exception) {
            return null
        }
    }
}