package com.secret.agentchat.presentation.chat.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.secret.agentchat.domain.models.Message

@Composable
fun MessageItem(
    message: Message,
    arrangement: Arrangement.Horizontal,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement,
    ) {
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
