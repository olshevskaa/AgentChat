package com.secret.agentchat.presentation.search_user

import com.secret.agentchat.domain.models.User

sealed interface SearchUserAction {
    data class UpdateQuery(val query: String): SearchUserAction
    data class NavigateToChat(val recipient: User): SearchUserAction
}