package com.secret.agentchat.presentation.chat

sealed interface ChatAction {
    data class SendMessage(val message: String) : ChatAction
    data class UpdateMessage(val newMessage: String) : ChatAction

}