package io.wwdaigo.domain.entities

data class Currency(
        val id: String,
        val name: String,
        val symbol: String,
        val decimals: Int)