package com.secret.agentchat.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.R
import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.requests.LoginRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _eventChannel = Channel<LoginEvents>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when(action) {
            is LoginAction.OnLoginClick -> login()
            is LoginAction.UpdateEmail -> { _loginState.update { state -> state.copy(email = action.newEmail) } }
            is LoginAction.UpdatePassword -> { _loginState.update { state -> state.copy(password = action.newPassword) }  }
        }
    }

    private fun login(){
        viewModelScope.launch {
            _loginState.update { state -> state.copy(isLoading = true) }
            authRepo.login(LoginRequest(email = loginState.value.email, password = loginState.value.password))?.let {
                _eventChannel.send(LoginEvents.Success)
            } ?: _eventChannel.send(LoginEvents.Failure(UiText.StringResource(R.string.unable_to_login)))
            _loginState.update { state -> state.copy(isLoading = false) }
        }
    }
}