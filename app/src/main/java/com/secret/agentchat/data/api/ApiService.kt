package com.secret.agentchat.data.api

import com.secret.agentchat.domain.models.User
import com.secret.agentchat.domain.responses.ChatResponse
import com.secret.agentchat.domain.requests.CreateChatRequest
import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.requests.SendMessageRequest
import com.secret.agentchat.domain.responses.LoginResponse
import com.secret.agentchat.domain.responses.MessageResponse
import com.secret.agentchat.domain.responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("messages/{chatId}")
    suspend fun getMessages(
        @Path("chatId") chatId: String
    ): Response<List<MessageResponse>>

    @POST("chats/create")
    suspend fun createChat(
        @Body body: CreateChatRequest
    ): Response<ChatResponse>

    @GET("chats/{userA}/{userB}")
    suspend fun getChatByParticipants(
        @Path("userA") userA: String,
        @Path("userB") userB: String
    ): Response<ChatResponse>

    @GET("chats/user/{userId}")
    suspend fun getChats(
        @Path("userId") userId: String
    ): Response<List<ChatResponse>>

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: String
    ): Response<User>

    @GET("users/search")
    suspend fun searchUsers(
        @Query("username") username: String
    ): Response<List<User>>

}
