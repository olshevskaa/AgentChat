package com.secret.agentchat.domain.repositories

import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.responses.AuthResponse

interface AuthRepo {
    suspend fun login(request: LoginRequest): AuthResponse?
    suspend fun register(request: RegisterRequest): AuthResponse?
}