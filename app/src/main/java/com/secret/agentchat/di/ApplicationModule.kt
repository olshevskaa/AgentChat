package com.secret.agentchat.di

import android.os.Build
import com.secret.agentchat.data.api.ChatWebSocketListener
import com.secret.agentchat.data.api.RetrofitClient
import com.secret.agentchat.data.api.WebSocketClient
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.data.keystore.KeyStoreHelper
import com.secret.agentchat.data.repositories.AuthRepoImpl
import com.secret.agentchat.data.repositories.ChatRepoImpl
import com.secret.agentchat.data.repositories.MessageRepoImpl
import com.secret.agentchat.data.repositories.UserRepoImpl
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.repositories.ChatRepo
import com.secret.agentchat.domain.repositories.MessageRepo
import com.secret.agentchat.domain.repositories.UserRepo
import com.secret.agentchat.domain.utils.validators.EmailPatternValidator
import com.secret.agentchat.domain.utils.validators.PatternValidator
import com.secret.agentchat.domain.utils.validators.UserDataValidator
import okhttp3.WebSocketListener
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.security.KeyStore

val applicationModule = module {
    factory { KeyStore.getInstance("AndroidKeyStore").apply { load(null) } }

    factory { RetrofitClient(get()).authService }

    factory { RetrofitClient(get()).apiService }

    factory { KeyStoreHelper(get()) }

    factory { SharedPref(androidContext()) }

    factory { CryptoHelper(get()) }

    factory<AuthRepo> { AuthRepoImpl(get(), get(), get()) }

    factory<UserRepo> { UserRepoImpl(get(), get()) }

    factory<MessageRepo> { MessageRepoImpl(get(), get(), get()) }

    factory<ChatRepo> { ChatRepoImpl(get(), get(), get()) }

    single<PatternValidator>{ EmailPatternValidator }

    single { UserDataValidator(get()) }

    single{ ChatWebSocketListener(get()) }

    single<WebSocketClient>{ WebSocketClient(RetrofitClient.WS_URL) }
}
