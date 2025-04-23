package com.secret.agentchat.presentation.search_user

import com.secret.agentchat.domain.models.User

data class SearchUserState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
)