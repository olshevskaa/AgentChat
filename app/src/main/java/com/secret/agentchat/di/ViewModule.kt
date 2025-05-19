package com.secret.agentchat.di

import androidx.lifecycle.SavedStateHandle
import com.secret.agentchat.presentation.chat.ChatViewModel
import com.secret.agentchat.presentation.chat_list.ChatListViewModel
import com.secret.agentchat.presentation.login.LoginViewModel
import com.secret.agentchat.presentation.main.MainViewModel
import com.secret.agentchat.presentation.register.RegisterViewModel
import com.secret.agentchat.presentation.search_user.SearchUserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        ChatListViewModel(get(), get(), get())
    }

    viewModel {
        SearchUserViewModel(get(), get())
    }

    viewModel {
        LoginViewModel(get(), get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }

    viewModel { (savedStateHandle: SavedStateHandle) ->
        ChatViewModel(
            messagesRepo = get(),
            userRepo = get(),
            savedStateHandle = savedStateHandle,
            sharedPref = get(),
            chatListener = get(),
            chatRepo = get(),
            cryptoHelper = get()
        )
    }

    viewModel {
        MainViewModel(get(), get(), get())
    }
}