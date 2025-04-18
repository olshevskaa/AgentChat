package com.secret.agentchat.domain.repositories

interface UserRepo {
    suspend fun getUserDetails(userId: String): String
}