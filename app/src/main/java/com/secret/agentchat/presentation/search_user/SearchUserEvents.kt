package com.secret.agentchat.presentation.search_user

import com.secret.agentchat.core.presentation.UiText

sealed interface SearchUserEvents {
    data class ShowError(val message: UiText): SearchUserEvents
}