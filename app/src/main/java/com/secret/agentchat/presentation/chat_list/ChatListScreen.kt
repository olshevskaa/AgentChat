package com.secret.agentchat.presentation.chat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.secret.agentchat.core.presentation.ObserveAsEvents
import com.secret.agentchat.presentation.chat_list.components.ChatListItem
import com.secret.agentchat.presentation.chat_list.components.ChatListTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatListScreen(
    toChat: (String) -> Unit,
    toSearch: () -> Unit,
    viewModel: ChatListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.) { }
    
    Scaffold(
        topBar = {
            ChatListTopBar(
                onSearchClick = toSearch
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(state.chats) { chat ->
                    ChatListItem(
                        chat = chat,
                        onClick = { toChat(chat.id) }
                    )
                }
            }
        }
    }
}
