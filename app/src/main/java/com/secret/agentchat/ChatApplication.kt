package com.secret.agentchat

import android.app.Application
import com.secret.agentchat.di.applicationModule
import org.koin.core.context.startKoin

class ChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(applicationModule)
        }
    }
}