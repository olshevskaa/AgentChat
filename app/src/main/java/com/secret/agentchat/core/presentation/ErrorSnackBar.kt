package com.secret.agentchat.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun ErrorSnackBar(snackBarHostState: SnackbarHostState) {
    SnackbarHost(snackBarHostState){
        Snackbar(
            snackbarData = it,
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.secondary
        )
    }
}