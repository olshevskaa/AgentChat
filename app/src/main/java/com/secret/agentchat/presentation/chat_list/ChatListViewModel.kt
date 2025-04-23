package com.secret.agentchat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.domain.repositories.ChatRepo
import com.secret.agentchat.presentation.login.LoginEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.secret.agentchat.R

class ChatListViewModel(
    private val chatRepo: ChatRepo
) : ViewModel() {

    private val _eventChannel = Channel<LoginEvents>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatListState())
    val state = _state.asStateFlow()

    init {
        loadChats()
    }

    fun loadChats(){
        viewModelScope.launch {
            _state.update { state -> state.copy(isLoading = true) }
            val chats = withContext(Dispatchers.IO){
                chatRepo.getChats()
            }
            if(chats != null){
                _state.update { state -> state.copy(chats = chats) }
            }else{
                _eventChannel.send(LoginEvents.Failure(UiText.StringResource(R.string.unable_to_fetch)))
            }
            _state.update { state -> state.copy(isLoading = false) }
        }
    }
}