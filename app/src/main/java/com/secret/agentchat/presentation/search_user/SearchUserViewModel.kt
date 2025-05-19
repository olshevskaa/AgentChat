package com.secret.agentchat.presentation.search_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.domain.repositories.ChatRepo
import com.secret.agentchat.domain.repositories.UserRepo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.secret.agentchat.R
import com.secret.agentchat.data.datastore.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


@OptIn(FlowPreview::class)
class SearchUserViewModel(
    private val userRepo: UserRepo,
    private val chatRepo: ChatRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUserState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<SearchUserEvents>()
    val events = _eventChannel.receiveAsFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init {
        viewModelScope.launch {
            _query.debounce(500).filter { it.isNotEmpty() }.collectLatest { query ->
                _state.update { it.copy(isLoading = true) }
                val users = withContext(Dispatchers.IO){
                    userRepo.searchUsers(query)
                }
                if(users != null){
                    _state.update { it.copy(users = users) }
                }else{
                    _eventChannel.send(SearchUserEvents.ShowError(UiText.StringResource(R.string.unable_to_fetch_users)))
                }

                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAction(action: SearchUserAction) {
        when (action) {
            is SearchUserAction.NavigateToChat -> {
                viewModelScope.launch{
                    val chat = withContext(Dispatchers.IO){
                        chatRepo.getChatByParticipants(action.recipient.id)
                    }
                    if(chat!= null){
                        _eventChannel.send(
                            SearchUserEvents.NavigateToChat(chatId = chat.id, userId = action.recipient.id)
                        )
                    }else{
                        _eventChannel.send(
                            SearchUserEvents.NavigateToChat(chatId = "", userId = action.recipient.id)
                        )
                    }
                }
            }
            is SearchUserAction.UpdateQuery -> {
                _query.tryEmit(action.query)
            }
        }
    }

}