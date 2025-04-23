package com.secret.agentchat.domain.utils.validators

data class PasswordValidationState(
    val hasMinimumLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
){
    val isValidPassword: Boolean
        get() = hasMinimumLength && hasNumber && hasUpperCaseCharacter && hasLowerCaseCharacter
}
