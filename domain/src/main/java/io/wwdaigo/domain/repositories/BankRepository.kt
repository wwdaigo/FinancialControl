package io.wwdaigo.domain.repositories

import io.wwdaigo.domain.entities.Bank

interface BankRepository {
    fun getBank(): Bank?
    fun insertBank(bank: Bank)
    fun updateBank(bank: Bank)
    fun deleteBank(bank: Bank)
}