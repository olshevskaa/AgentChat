package com.secret.agentchat.presentation.search_user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.secret.agentchat.core.presentation.AgentTextField
import com.secret.agentchat.presentation.search_user.components.SearchUserTopBar
import com.secret.agentchat.R
import com.secret.agentchat.core.presentation.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.secret.agentchat.presentation.search_user.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUserScreen(
    goBack: () -> Unit,
    toChat: () -> Unit,
    viewModel: SearchUserViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is SearchUserEvents.ShowError -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            SearchUserTopBar(onBackClick = goBack)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            AgentTextField(
                modifier = Modifier.fillMaxWidth().height(60.dp),
                text = query,
                onTextChange = { newText -> viewModel.onAction(SearchUserAction.UpdateQuery(newText)) },
                label = stringResource(R.string.username)
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn{
                items(state.users) { user ->
                    UserItem(user) {
                        toChat()
                    }
                }
            }
        }
    }
}