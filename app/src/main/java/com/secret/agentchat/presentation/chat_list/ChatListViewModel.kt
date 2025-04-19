package com.secret.agentchat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.domain.repositories.ChatRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val chatRepo: ChatRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatListState())
    val state = _state.asStateFlow()

    init {
        loadChats()
    }

    fun loadChats(){
        viewModelScope.launch {
            _state.update { state -> state.copy(isLoading = true) }
            chatRepo.getChats()?.let {
                _state.update { state -> state.copy(chats = it, isLoading = false) }
            }
        }
    }
}