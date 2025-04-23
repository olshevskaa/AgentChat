package com.secret.agentchat.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
)