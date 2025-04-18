package com.secret.agentchat.presentation.chat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.secret.agentchat.presentation.chat_list.components.ChatListItem
import com.secret.agentchat.presentation.chat_list.components.ChatListTopBar

@Composable
fun ChatListScreen(
    toRoom: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            ChatListTopBar(
                onSearchClick = { /* Handle search click */ }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(getSampleChats()) { chat ->
                    ChatListItem(
                        chat = chat,
                        onClick = { toRoom(chat.id) }
                    )
                }
            }
        }
    }
}

data class ChatPreview(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timestamp: String
)

fun getSampleChats(): List<ChatPreview> {
    return listOf(
        ChatPreview("1", "Alice", "Hey, how are you?", "10:30 AM"),
        ChatPreview("2", "Bob", "Did you see the latest update?", "Yesterday"),
        ChatPreview("3", "Charlie", "Let's meet tomorrow", "2 days ago")
    )
}
