package io.wwdaigo.domain.entities

import java.util.*

data class Income(
        val id: String,
        val category: IncomeCategory?,
        val name: String,
        val bank: Bank,
        val source: Source?,
        val value: Double,
        val dateTime: Date)