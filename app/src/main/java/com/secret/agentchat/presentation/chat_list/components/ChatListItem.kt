package com.secret.agentchat.presentation.chat_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.secret.agentchat.presentation.chat_list.ChatPreview

@Composable
fun ChatListItem(
    chat: ChatPreview,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column{
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chat.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = chat.lastMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = chat.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }
}