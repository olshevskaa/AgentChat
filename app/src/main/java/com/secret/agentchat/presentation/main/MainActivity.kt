package com.secret.agentchat.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.secret.agentchat.presentation.navigation.NavigationRoot
import com.secret.agentchat.ui.theme.AgentChatTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val token = withContext(Dispatchers.IO){ mainViewModel.getToken() }
            enableEdgeToEdge()
            setContent {
                AgentChatTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) {
                        NavigationRoot(token)
                    }
                }
            }
        }
    }
}
