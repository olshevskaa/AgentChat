package com.secret.agentchat.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgentTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    keyBoardType: KeyboardType = KeyboardType.Text,
    label: String,
    isCounterEnabled: Boolean = false,
    maxLength: Int = 30,
    maxLines: Int = 1
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            value = text,
            placeholder = {
                Text(text = label, color = MaterialTheme.colorScheme.secondary)
            },
            onValueChange = { newText: String ->
                if (newText.length <= maxLength) {
                    onTextChange(newText)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType
            ),
            maxLines = maxLines,
            label = { Text(text = label, color = MaterialTheme.colorScheme.secondary) },
            shape = RoundedCornerShape(16.dp),
            singleLine = maxLines == 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary
            ),
        )

        if (isCounterEnabled) {
            Text(
                modifier = Modifier
                    .padding(end = 4.dp, top = 4.dp)
                    .fillMaxWidth(),
                text = "${text.length} / $maxLength",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                textAlign = TextAlign.End,
            )
        }
    }
}