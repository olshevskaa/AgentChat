package com.secret.agentchat.di

import com.secret.agentchat.data.api.RetrofitClient
import com.secret.agentchat.data.crypto.CryptoHelper
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.data.keystore.KeyStoreHelper
import org.koin.dsl.module
import java.security.KeyStore

val applicationModule = module {
    factory { KeyStore.getInstance("AndroidKeyStore").apply { load(null) } }

    factory { RetrofitClient.api }

    factory { KeyStoreHelper(get()) }

    factory { SharedPref(get()) }

    factory { CryptoHelper(get()) }
}
