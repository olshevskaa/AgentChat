package com.secret.agentchat.data.api

import com.secret.agentchat.data.datastore.SharedPref
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val sharedPref: SharedPref) {

    private val authInterceptor = AuthInterceptor {
        sharedPref.getToken().first().toString()
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authInterceptor)
        .build()

    private val authClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val authService : AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    companion object{
        const val BASE_URL = "http://10.0.2.2:5000/"
        const val WS_URL = "ws://10.0.2.2:5000/"

//        const val BASE_URL = "https://chat-backend-n1pd.onrender.com/"
//        const val WS_URL = "ws://chat-backend-n1pd.onrender.com/"
    }
}
