package com.secret.agentchat.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.secret.agentchat.core.presentation.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import com.secret.agentchat.R
import com.secret.agentchat.core.presentation.AgentButton
import com.secret.agentchat.core.presentation.AgentPasswordTextField
import com.secret.agentchat.core.presentation.AgentTextField
import com.secret.agentchat.core.presentation.ErrorSnackBar
import kotlinx.coroutines.launch

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    ObserveAsEvents(viewModel.events) { event ->
        when(event){
            is LoginEvents.Failure -> {
                scope.launch {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }
            }
            LoginEvents.NavigateToSignUp -> onSignUpClick()
            LoginEvents.Success -> onLoginSuccess()
        }
    }

    val state by viewModel.loginState.collectAsState()

    LoginScreen(
        onAction = { action -> viewModel.onAction(action) },
        onSignUpClick = onSignUpClick,
        state = state,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun LoginScreen(
    onAction: (LoginAction) -> Unit,
    onSignUpClick: () -> Unit,
    state: LoginState,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { ErrorSnackBar(snackBarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.welcome_to_agent_chat),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )

            Column {
                AgentTextField(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    text = state.email,
                    onTextChange = { onAction(LoginAction.UpdateEmail(it)) },
                    keyBoardType = KeyboardType.Email,
                    label = stringResource(R.string.email)
                )

                Spacer(modifier = Modifier.height(10.dp))

                AgentPasswordTextField(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    password = state.password,
                    onPasswordChange = { onAction(LoginAction.UpdatePassword(it)) },
                    label = stringResource(R.string.password)
                )

                Spacer(modifier = Modifier.height(30.dp))

                AgentButton(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    textId = R.string.sign_in,
                    onClick = { onAction(LoginAction.OnLoginClick) },
                    isLoading = state.isLoading,
                )
            }

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    append(stringResource(id = R.string.dont_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.sign_up)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(stringResource(id = R.string.sign_up))
                    }
                }
            }
            Box{
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            onSignUpClick()
                        }
                    }
                )
            }
        }
    }
}