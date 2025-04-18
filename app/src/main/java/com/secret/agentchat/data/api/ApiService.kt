package com.secret.agentchat.data.api

import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.requests.SendMessageRequest
import com.secret.agentchat.domain.responses.AuthResponse
import com.secret.agentchat.domain.responses.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponse>

    @GET("messages")
    suspend fun getMessages(@Header("Authorization") token: String): Response<List<MessageResponse>>

    @POST("messages/send")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body body: SendMessageRequest
    ): Response<Any>
}
