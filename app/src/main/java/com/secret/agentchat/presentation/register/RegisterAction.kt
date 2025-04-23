package com.secret.agentchat.presentation.register

sealed interface RegisterAction {
    data object OnSignUpClick : RegisterAction
    data class UpdateEmail(val newEmail: String) : RegisterAction
    data class UpdatePassword(val newPassword: String) : RegisterAction
    data class UpdateUsername(val username: String) : RegisterAction
}