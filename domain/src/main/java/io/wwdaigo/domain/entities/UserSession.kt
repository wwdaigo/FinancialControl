package io.wwdaigo.domain.entities

data class UserSession(
        val token: String,
        val authMethod: AuthMethod)