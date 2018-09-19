package io.wwdaigo.domain.repositories

import io.reactivex.Observable
import io.wwdaigo.domain.entities.UserSession

interface AuthRepository {
    fun loginWithEmail(email: String, password: String): Observable<UserSession>
    fun loginWithFacebook(token: String): Observable<UserSession>
    fun loginWithGoogle(token: String): Observable<UserSession>

    fun saveSession(userSession: UserSession)
    fun getSession(): UserSession?
    fun deleteSession()
}