package io.wwdaigo.domain.repositories

import io.wwdaigo.domain.entities.User

interface UserRepository{
    fun createUser(user: User, password: String)
    fun getLoggedUser(): User?
}