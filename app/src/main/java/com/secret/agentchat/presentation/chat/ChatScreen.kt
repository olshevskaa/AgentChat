package com.secret.agentchat.presentation.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.secret.agentchat.core.presentation.AgentTextField
import com.secret.agentchat.presentation.chat.components.ChatTopBar
import com.secret.agentchat.domain.models.Message
import org.koin.androidx.compose.koinViewModel
import com.secret.agentchat.R
import com.secret.agentchat.presentation.chat.components.MessageItem

@Composable
fun ChatScreenRoot(
    goBack: () -> Unit,
    viewModel: ChatViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    ChatScreen(
        state = state,
        onAction = viewModel::onAction,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatState,
    onAction: (ChatAction) -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            ChatTopBar(
                goBack = goBack,
                username = state.chatPartner.username,
            )
        },
        bottomBar = {
            MessageTextField(
                message = state.currentMessage,
                onTextChange = { onAction(ChatAction.UpdateMessage(it)) },
                label = stringResource(R.string.message)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            items(state.messages) { message ->
                MessageItem(
                    message = message,
                    arrangement = if (message.senderId != state.myId) Arrangement.Start else Arrangement.End,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageTextField(
    message: String,
    label: String,
    onTextChange: (String) -> Unit,
    keyBoardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        value = message,
        placeholder = {
            Text(text = label, color = MaterialTheme.colorScheme.secondary)
        },
        onValueChange = { newText: String ->
            onTextChange(newText)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType
        ),
        trailingIcon = {
            Image(
                painter = painterResource(R.drawable.send_icon),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer),
                contentDescription = stringResource(R.string.send_message)
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary
        ),
    )
}