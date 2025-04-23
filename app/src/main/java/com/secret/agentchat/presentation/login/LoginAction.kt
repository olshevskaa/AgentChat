package com.secret.agentchat.presentation.login

sealed interface LoginAction {
    data object OnLoginClick : LoginAction
    data class UpdateEmail(val newEmail: String) : LoginAction
    data class UpdatePassword(val newPassword: String) : LoginAction
}