package com.secret.agentchat.presentation.search_user

sealed interface SearchUserAction {
    data class UpdateQuery(val query: String): SearchUserAction
    data object CreateNewChat: SearchUserAction
}