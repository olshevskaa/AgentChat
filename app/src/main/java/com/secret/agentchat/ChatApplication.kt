package com.secret.agentchat

import android.app.Application
import com.secret.agentchat.di.applicationModule
import com.secret.agentchat.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(applicationModule, viewModule)
            androidContext(this@ChatApplication)
        }
    }
}