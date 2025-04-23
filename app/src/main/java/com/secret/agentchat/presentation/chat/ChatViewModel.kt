package com.secret.agentchat.presentation.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.domain.repositories.ChatRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.secret.agentchat.R
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.repositories.UserRepo
import com.secret.agentchat.presentation.chat_list.ChatListEvents
import com.secret.agentchat.presentation.chat_list.ChatListState
import com.secret.agentchat.presentation.navigation.Routes.Chat
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class ChatViewModel(
    private val chatRepo: ChatRepo,
    private val messagesRepo: MessageRepo,
    private val userRepo: UserRepo,
    private val savedStateHandle: SavedStateHandle,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _eventChannel = Channel<ChatEvents>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private val chatArgs = savedStateHandle.toRoute<Chat>()

    init {
        setup()
    }

    fun onAction(action: ChatAction){
        when(action){
            is ChatAction.SendMessage -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val params = SendMessageParams()
                        messagesRepo.sendMessage(action.message, action.chatId)
                    }
                    _eventChannel.send(ChatEvents.MessageSent)
                }
            }
            is ChatAction.UpdateMessage -> { _state.update { state -> state.copy(currentMessage = action.newMessage) } }
        }
    }

    private fun setup(){
        viewModelScope.launch {
            _state.update { state -> state.copy(isChatLoading = true) }
            val user = async(Dispatchers.IO){
                chatArgs.userId?.let{
                    userRepo.getUser(it)
                }
            }

            val messages = async(Dispatchers.IO){
                chatArgs.chatId?.let{
                    messagesRepo.getMessages(it)
                } ?: emptyList()
            }

            val myId = async(Dispatchers.IO){
                sharedPref.getUserId().first().toString()
            }

            _state.update { state ->
                state.copy(
                    myId = myId.await(),
                    chatPartner = user.await(),
                    messages = messages.await(),
                    isChatLoading = false
                )
            }
        }
    }
}