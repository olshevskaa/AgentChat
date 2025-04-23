package com.secret.agentchat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.secret.agentchat.presentation.chat_list.ChatListScreen
import com.secret.agentchat.presentation.login.LoginScreen
import com.secret.agentchat.presentation.login.LoginScreenRoot
import com.secret.agentchat.presentation.register.RegisterScreenRoot
import com.secret.agentchat.presentation.search_user.SearchUserScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SearchUser){
        composable<Routes.ChatList>{
            ChatListScreen(
                toChat = {  },
                toSearch = { navController.navigate(Routes.SearchUser) }
            )
        }

        composable<Routes.SearchUser>{
            SearchUserScreen(
                toChat = {  },
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
                onLoginClick = { navController.navigate(Routes.Login) }
            )
        }
    }
}