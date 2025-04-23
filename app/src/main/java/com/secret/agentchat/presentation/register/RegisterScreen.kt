package com.secret.agentchat.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.secret.agentchat.R
import com.secret.agentchat.core.presentation.AgentButton
import com.secret.agentchat.core.presentation.AgentPasswordTextField
import com.secret.agentchat.core.presentation.AgentTextField
import com.secret.agentchat.core.presentation.ErrorSnackBar
import com.secret.agentchat.core.presentation.ObserveAsEvents
import com.secret.agentchat.domain.utils.validators.UserDataValidator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignUpSuccess: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.events) { event ->
        when(event){
            is RegisterEvents.Failure ->  {
                scope.launch {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }
            }
            RegisterEvents.Success -> onSignUpSuccess()
        }
    }

    val state by viewModel.registerState.collectAsState()
    val email by viewModel.emailState.collectAsState()
    val password by viewModel.passwordState.collectAsState()

    RegisterScreen(
        onAction = { action -> viewModel.onAction(action) },
        state = state,
        snackBarState = snackBarHostState,
        email = email,
        password = password
    )
}

@Composable
fun RegisterScreen(
    onAction: (RegisterAction) -> Unit,
    state: RegisterState,
    email: String,
    password: String,
    snackBarState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { ErrorSnackBar(snackBarState) }
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
                    text = state.username,
                    onTextChange = { onAction(RegisterAction.UpdateUsername(it)) },
                    keyBoardType = KeyboardType.Text,
                    label = stringResource(R.string.username)
                )
                Spacer(modifier = Modifier.height(10.dp))

                AgentTextField(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    text = email,
                    onTextChange = { onAction(RegisterAction.UpdateEmail(it)) },
                    keyBoardType = KeyboardType.Email,
                    label = stringResource(R.string.email)
                )

                Spacer(modifier = Modifier.height(10.dp))

                AgentPasswordTextField(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    password = password,
                    onPasswordChange = { onAction(RegisterAction.UpdatePassword(it)) },
                    label = stringResource(R.string.password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordRequirement(
                    text = stringResource(
                        id = R.string.at_least_x_characters,
                        UserDataValidator.MIN_PASSWORD_LENGTH
                    ),
                    isValid = state.passwordValidationState.hasMinimumLength
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRequirement(
                    text = stringResource(
                        id = R.string.at_least_one_number,
                    ),
                    isValid = state.passwordValidationState.hasNumber
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRequirement(
                    text = stringResource(
                        id = R.string.contains_lowercase_character,
                    ),
                    isValid = state.passwordValidationState.hasLowerCaseCharacter
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRequirement(
                    text = stringResource(
                        id = R.string.contains_uppercase_character,
                    ),
                    isValid = state.passwordValidationState.hasUpperCaseCharacter
                )

                Spacer(modifier = Modifier.height(30.dp))

                AgentButton(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    textId = R.string.sign_up,
                    onClick = { onAction(RegisterAction.OnSignUpClick) },
                    isLoading = state.isLoading,
                    enabled = state.canRegister
                )
            }
        }
    }
}

@Composable
fun PasswordRequirement(
    modifier: Modifier = Modifier,
    text: String,
    isValid: Boolean = false
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = if(isValid) painterResource(R.drawable.check_icon) else painterResource(R.drawable.cross_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(if(isValid) Color.Green else Color.Red),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

    }
}