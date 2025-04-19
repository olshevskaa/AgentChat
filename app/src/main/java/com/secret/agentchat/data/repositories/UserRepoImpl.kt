package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.domain.repositories.UserRepo

class UserRepoImpl(
    private val api: ApiService
) : UserRepo {
    override suspend fun getUser(userId: String): String {
        return "user"
    }
}