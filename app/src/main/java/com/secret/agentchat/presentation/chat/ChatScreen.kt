package com.secret.agentchat.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.secret.agentchat.R
import com.secret.agentchat.presentation.chat.components.ChatTopBar
import com.secret.agentchat.presentation.chat.components.MessageItem
import org.koin.androidx.compose.koinViewModel

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
                modifier = Modifier.padding(bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()),
                message = state.currentMessage,
                onTextChange = { onAction(ChatAction.UpdateMessage(it)) },
                label = stringResource(R.string.message),
                sendMessage = { onAction(ChatAction.SendMessage(state.currentMessage)) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 10.dp)
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            items(state.messages) { message ->
                MessageItem(
                    message = message,
                    arrangement = if (message.senderId != state.userId) Arrangement.Start else Arrangement.End,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageTextField(
    modifier: Modifier,
    message: String,
    label: String,
    onTextChange: (String) -> Unit,
    keyBoardType: KeyboardType = KeyboardType.Text,
    sendMessage: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().height(50.dp),
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
                modifier = Modifier.clickable{ sendMessage() },
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