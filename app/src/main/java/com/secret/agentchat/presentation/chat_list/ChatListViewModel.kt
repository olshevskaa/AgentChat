package com.secret.agentchat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.secret.agentchat.domain.models.ChatPreview
import com.secret.agentchat.domain.repositories.UserRepo
import kotlinx.coroutines.flow.first

class ChatListViewModel(
    private val chatRepo: ChatRepo,
    private val userRepo: UserRepo,
    val sharedPref: SharedPref
) : ViewModel() {

    private val _eventChannel = Channel<ChatListEvents>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatListState())
    val state = _state.asStateFlow()

    init {
        loadChats()
    }

    fun navigateToChat(chat: ChatPreview){
        viewModelScope.launch {
            val userId = withContext(Dispatchers.IO){
                sharedPref.getUserId().first()
            }
            val recipientId = chat.participants.first { it != userId }
            _eventChannel.send(ChatListEvents.NavigateToChat(chat.id, recipientId))
        }
    }

    fun loadChats(){
        viewModelScope.launch {
            _state.update { state -> state.copy(isLoading = true) }
            val chats = withContext(Dispatchers.IO){
                val userId = sharedPref.getUserId().first()
                chatRepo.getChats()?.map { chat ->
                    val recipientId = chat.participants.first { it!= userId }
                    val user = userRepo.getUser(recipientId)
                    chat.copy(name = user?.username.toString())
                }
            }
            if(chats != null){
                _state.update { state -> state.copy(chats = chats) }
            }else{
                _eventChannel.send(ChatListEvents.Failure(UiText.StringResource(R.string.unable_to_fetch_chats)))
            }
            _state.update { state -> state.copy(isLoading = false) }
        }
    }
}