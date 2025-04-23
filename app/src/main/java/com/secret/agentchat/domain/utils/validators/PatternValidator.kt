package com.secret.agentchat.domain.utils.validators

interface PatternValidator {
    fun matches(value: String): Boolean
}