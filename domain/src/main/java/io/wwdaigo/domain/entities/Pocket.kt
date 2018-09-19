package io.wwdaigo.domain.entities

data class Pocket(
        val id: String,
        val name: String,
        val currency: Currency,
        val balance: Double): Source