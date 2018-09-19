package io.wwdaigo.domain.entities

import java.util.*

data class Outcome(
        val id: String,
        val category: OutcomeCategory,
        val name: String,
        val entries: List<ExpenseEntry>,
        val dateTime: Date)