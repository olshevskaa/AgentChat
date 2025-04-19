package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.AuthService
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.responses.LoginResponse
import com.secret.agentchat.domain.responses.RegisterResponse

class AuthRepoImpl(
    private val auth: AuthService
) : AuthRepo {

    override suspend fun login(request: LoginRequest): LoginResponse? {
        return try {
            val response = auth.login(request)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse? {
        return try {
            val response = auth.register(request)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
