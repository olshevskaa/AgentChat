package com.secret.agentchat.domain.models

data class User(
    val id: String,
    val username: String,
    val email: String,
    val publicKey: String
) {
    companion object {
        val EmptyUser = User(
            id = "",
            username = "",
            email = "",
            publicKey = ""
        )
    }
}