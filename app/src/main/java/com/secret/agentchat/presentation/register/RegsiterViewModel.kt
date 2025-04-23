package com.secret.agentchat.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.R
import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.data.crypto.AESUtils
import com.secret.agentchat.data.keystore.KeyStoreHelper
import com.secret.agentchat.domain.models.RegisterData
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.requests.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId

class RegisterViewModel(
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _eventChannel = Channel<RegisterEvents>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when(action) {
            is RegisterAction.OnSignUpClick -> signUp()
            is RegisterAction.UpdateEmail -> { _registerState.update { state -> state.copy(email = action.newEmail) } }
            is RegisterAction.UpdatePassword -> { _registerState.update { state -> state.copy(password = action.newPassword) }  }
            is RegisterAction.UpdateUsername -> { _registerState.update { state -> state.copy(username = action.username) }  }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _registerState.update { state -> state.copy(isLoading = true) }
            
            val result = withContext(Dispatchers.IO) {
                authRepo.register(
                    RegisterData(
                        username = registerState.value.username,
                        email = registerState.value.email,
                        password = registerState.value.password
                    )
                )
            }

            if (result != null) {
                _eventChannel.send(RegisterEvents.Success)
            } else {
                _eventChannel.send(RegisterEvents.Failure(UiText.StringResource(R.string.unable_to_register)))
            }
            
            _registerState.update { state -> state.copy(isLoading = false) }
        }
    }
}