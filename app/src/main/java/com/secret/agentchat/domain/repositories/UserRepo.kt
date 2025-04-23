package com.secret.agentchat.domain.repositories

import com.secret.agentchat.domain.models.User

interface UserRepo {
    suspend fun getUser(userId: String): User?
    suspend fun searchUsers(query: String): List<User>?
}