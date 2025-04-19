package com.secret.agentchat.domain.repositories

interface UserRepo {
    suspend fun getUser(userId: String): String
}