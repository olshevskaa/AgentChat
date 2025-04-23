package com.secret.agentchat.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secret.agentchat.R
import com.secret.agentchat.core.presentation.UiText
import com.secret.agentchat.domain.models.RegisterData
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.utils.validators.UserDataValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val authRepo: AuthRepo,
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    private val _eventChannel = Channel<RegisterEvents>()
    val events = _eventChannel.receiveAsFlow()

    init {
        emailState.onEach { email ->
            val isValidEmail = userDataValidator.isValidEmail(email.toString())
            _registerState.update { state ->
                state.copy(
                    isMailValid = isValidEmail,
                    canRegister = isValidEmail
                            && state.passwordValidationState.isValidPassword
                            && !state.isLoading
                )
            }
        }.launchIn(viewModelScope)

        passwordState.onEach { password ->
            val passwordValidationState = userDataValidator.validatePassword(password.toString())
            _registerState.update { state ->
                state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isMailValid
                            && passwordValidationState.isValidPassword
                            && !state.isLoading
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when(action) {
            is RegisterAction.OnSignUpClick -> signUp()
            is RegisterAction.UpdateEmail -> { _emailState.tryEmit(action.newEmail) }
            is RegisterAction.UpdatePassword -> { _passwordState.tryEmit(action.newPassword) }
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
                        email = emailState.value,
                        password = passwordState.value
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