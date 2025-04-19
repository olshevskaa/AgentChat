package com.secret.agentchat.domain.repositories

import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.responses.LoginResponse
import com.secret.agentchat.domain.responses.RegisterResponse

interface AuthRepo {
    suspend fun login(request: LoginRequest): LoginResponse?
    suspend fun register(request: RegisterRequest): RegisterResponse?
}