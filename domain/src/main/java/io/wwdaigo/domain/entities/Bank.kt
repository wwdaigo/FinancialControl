package io.wwdaigo.domain.entities

data class Bank (
        val id: String,
        val name: String,
        val currency: Currency,
        val balance: Double)