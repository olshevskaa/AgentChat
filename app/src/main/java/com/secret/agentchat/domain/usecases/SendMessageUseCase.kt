package com.secret.agentchat.domain.usecases

import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.repositories.ChatRepo
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.requests.CreateChatRequest

class SendMessageUseCase(
    private val messageRepo: MessageRepo,
    private val chatRepo: ChatRepo
) {
    suspend fun execute(params: SendMessageParams) {
        if(params.chatId.isNotBlank()){
            messageRepo.sendMessage(params)
        }else{
            val request = CreateChatRequest(userA = params.userId, userB = params.recipientId)
            chatRepo.createChat(request)
        }
    }
}