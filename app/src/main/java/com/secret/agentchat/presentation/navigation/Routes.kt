package com.secret.agentchat.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object ChatList: Routes()
    @Serializable
    data class Chat(val userId: String? = null, val chatId: String? = null): Routes()
    @Serializable
    data object SearchUser: Routes()
    @Serializable
    data object Login: Routes()
    @Serializable
    data object SignUp: Routes()
}