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
import com.secret.agentchat.data.api.ChatWebSocketListener
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.models.User
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.repositories.UserRepo
import com.secret.agentchat.domain.usecases.SendMessageUseCase
import com.secret.agentchat.presentation.chat_list.ChatListEvents
import com.secret.agentchat.presentation.chat_list.ChatListState
import com.secret.agentchat.presentation.navigation.Routes.Chat
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

class ChatViewModel(
    private val messagesRepo: MessageRepo,
    private val userRepo: UserRepo,
    private val savedStateHandle: SavedStateHandle,
    private val sharedPref: SharedPref,
    private val chatListener: ChatWebSocketListener,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _eventChannel = Channel<ChatEvents>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private val chatArgs = savedStateHandle.toRoute<Chat>()

    init {
        setup()
        observeMessages()
    }

    fun onAction(action: ChatAction){
        when(action){
            is ChatAction.SendMessage -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val currentState = state.value
                        val params = SendMessageParams(
                            chatId = currentState.chatId,
                            userId = currentState.userId,
                            recipientId = currentState.chatPartner.id,
                            message = action.message,
                            recipientPublicKey = currentState.chatPartner.publicKey
                        )
                        sendMessageUseCase.execute(params)
                        _state.update { state -> state.copy(messages = state.) }
                    }
                }
            }
            is ChatAction.UpdateMessage -> { _state.update { state -> state.copy(currentMessage = action.newMessage) } }
        }
    }

    private fun observeMessages(){
        viewModelScope.launch {
            chatListener.messages.collectLatest {
                _state.update { state -> state.copy(messages = it) }
            }
        }
    }

    private fun setup(){
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isChatLoading = true,
                    chatId = chatArgs.chatId
                )
            }

            val chatPartner = async(Dispatchers.IO){
                userRepo.getUser(chatArgs.recipientId) ?: User.EmptyUser
            }

            val messages = async(Dispatchers.IO){
                messagesRepo.getMessages(chatArgs.chatId)
            }

            val userId = async(Dispatchers.IO){
                sharedPref.getUserId().first().toString()
            }

            _state.update { state ->
                state.copy(
                    userId = userId.await(),
                    chatPartner = chatPartner.await(),
                    messages = messages.await(),
                    isChatLoading = false
                )
            }
        }
    }
}