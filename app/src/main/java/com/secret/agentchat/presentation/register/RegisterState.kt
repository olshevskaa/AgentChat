package com.secret.agentchat.presentation.register

import com.secret.agentchat.domain.utils.validators.PasswordValidationState

data class RegisterState(
    val username: String = "",
    val isLoading: Boolean = false,
    val canRegister: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isMailValid: Boolean = false,
)