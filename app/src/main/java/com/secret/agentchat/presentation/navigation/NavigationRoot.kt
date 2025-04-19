package com.secret.agentchat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.secret.agentchat.presentation.chat_list.ChatListScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.ChatList){
        composable<Routes.ChatList>{
            ChatListScreen(
                toChat = {  }
            )
        }
    }
}