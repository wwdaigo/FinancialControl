package io.wwdaigo.domain.businessrules

import io.reactivex.Observable
import io.wwdaigo.domain.commons.Result
import io.wwdaigo.domain.entities.UserSession

interface Login {
    val callback: Callback

    fun loginWithEmail(email: String, password: String)
    fun loginWithFacebook(token: String)
    fun loginWithGoogle(token: String)

    interface Callback {
        val userSession: Observable<Result<UserSession>>
    }
}