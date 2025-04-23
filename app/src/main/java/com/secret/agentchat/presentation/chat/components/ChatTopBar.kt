package com.secret.agentchat.presentation.chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.secret.agentchat.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    goBack: () -> Unit,
    username: String,
    userPictureUrl: String? = null
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    if (userPictureUrl != null) {
                        // If you have a way to load images from URLs, use it here
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.profile_icon),
                            contentDescription = "Default profile picture",
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Text(
                    text = username,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}