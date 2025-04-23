package com.secret.agentchat.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object ChatList: Routes()
    @Serializable
    data class ChatRoom(val userId: String): Routes()
    @Serializable
    data object SearchUser: Routes()
    @Serializable
    data object Login: Routes()
    @Serializable
    data object SignUp: Routes()
}