package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.ApiService
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.responses.AuthResponse

class AuthRepoImpl(
    private val api: ApiService
) : AuthRepo {

    override suspend fun login(request: LoginRequest): AuthResponse? {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun register(request: RegisterRequest): AuthResponse? {
        return try {
            val response = api.register(request)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
