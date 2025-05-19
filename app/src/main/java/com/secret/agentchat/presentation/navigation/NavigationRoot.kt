package com.secret.agentchat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.secret.agentchat.presentation.chat.ChatScreenRoot
import com.secret.agentchat.presentation.chat_list.ChatListScreen
import com.secret.agentchat.presentation.login.LoginScreen
import com.secret.agentchat.presentation.login.LoginScreenRoot
import com.secret.agentchat.presentation.register.RegisterScreenRoot
import com.secret.agentchat.presentation.search_user.SearchUserScreen

@Composable
fun NavigationRoot(token: String) {
    val navController = rememberNavController()

    val startRoute = if(token.isNotBlank()) Routes.ChatList else Routes.Login

    NavHost(navController = navController, startDestination = startRoute){
        composable<Routes.ChatList>{
            ChatListScreen(
                toChat = { userId, chatId ->
                    navController.navigate(Routes.Chat(chatId = chatId, recipientId = userId))
                },
                toSearch = { navController.navigate(Routes.SearchUser) }
            )
        }

        composable<Routes.SearchUser>{
            SearchUserScreen(
                toChat = { userId, chatId ->
                    navController.navigate(Routes.Chat(chatId = chatId, recipientId = userId))
                },
                goBack = { navController.popBackStack() }
            )
        }

        composable<Routes.Login>{
            LoginScreenRoot(
                onLoginSuccess = { navController.navigate(Routes.ChatList) },
                onSignUpClick = { navController.navigate(Routes.SignUp) }
            )
        }

        composable<Routes.SignUp>{
            RegisterScreenRoot(
                onSignUpSuccess = { navController.navigate(Routes.ChatList) },
            )
        }

        composable<Routes.Chat>{
            ChatScreenRoot(
                goBack = { navController.popBackStack() }
            )
        }
    }
}