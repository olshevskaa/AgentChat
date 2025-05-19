package com.secret.agentchat.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.data.api.ChatWebSocketListener
import com.secret.agentchat.data.api.WebSocketClient
import com.secret.agentchat.data.api.formatWebSocketEvent
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.WebSocketEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val webClient: WebSocketClient,
    private val chatListener: ChatWebSocketListener,
    private val sharedPref: SharedPref
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            webClient.connect(chatListener)
            val userId = sharedPref.getUserId().first() ?: ""
            val event = WebSocketEvent("join", userId).formatWebSocketEvent()
            webClient.send(event)
        }
    }

    suspend fun getToken() : String {
        return sharedPref.getToken().first() ?: ""
    }

    override fun onCleared() {
        super.onCleared()
        webClient.close()
    }
}