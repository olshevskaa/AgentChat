package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.domain.models.User
import com.secret.agentchat.domain.repositories.UserRepo
import kotlinx.coroutines.flow.first

class UserRepoImpl(
    private val api: ApiService,
    private val sharedPref: SharedPref
) : UserRepo {
    override suspend fun getUser(userId: String): User? {
        try {
            val response = api.getUser(userId)
            if(response.isSuccessful) return response.body()
            return null
        }catch(e: Exception) {
            return null
        }
    }

    override suspend fun searchUsers(query: String): List<User>? {
        try {
            val userId = sharedPref.getUserId().first()
            val response = api.searchUsers(query)
            if(response.isSuccessful) return response.body()?.filter { it.id != userId }
            return null
        }catch(e: Exception) {
            return null
        }
    }
}