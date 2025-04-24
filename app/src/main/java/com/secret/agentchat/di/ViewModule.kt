package com.secret.agentchat.di

import com.secret.agentchat.presentation.chat.ChatViewModel
import com.secret.agentchat.presentation.chat_list.ChatListViewModel
import com.secret.agentchat.presentation.login.LoginViewModel
import com.secret.agentchat.presentation.register.RegisterViewModel
import com.secret.agentchat.presentation.search_user.SearchUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        ChatListViewModel(get())
    }

    viewModel {
        SearchUserViewModel(get(), get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }

    viewModel {
        ChatViewModel(get(), get())
    }
}