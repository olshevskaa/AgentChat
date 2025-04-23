package com.secret.agentchat.domain.utils.validators

import android.util.Patterns

object EmailPatternValidator : PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

}