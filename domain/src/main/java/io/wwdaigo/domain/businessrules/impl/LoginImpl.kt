package io.wwdaigo.domain.businessrules.impl

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.wwdaigo.domain.businessrules.Login
import io.wwdaigo.domain.commons.Result
import io.wwdaigo.domain.entities.UserSession
import io.wwdaigo.domain.repositories.AuthRepository

class LoginImpl(private val repository: AuthRepository): Login, Login.Callback {

    override val callback: Login.Callback
        get() = this

    private val userSessionSubject = PublishSubject.create<Result<UserSession>>()
    override val userSession: Observable<Result<UserSession>>
        get() = userSessionSubject

    override fun loginWithEmail(email: String, password: String) {
        repository.loginWithEmail(email, password)
                .subscribe({
                    handleUserSession(it)
                }, {
                    userSessionSubject.onNext(Result.Error(it))
                })
    }

    override fun loginWithFacebook(token: String) {
        repository.loginWithFacebook(token)
                .subscribe({
                    handleUserSession(it)
                }, {
                    userSessionSubject.onNext(Result.Error(it))
                })
    }

    override fun loginWithGoogle(token: String) {
        repository.loginWithGoogle(token)
                .subscribe({
                    handleUserSession(it)
                }, {
                    userSessionSubject.onNext(Result.Error(it))
                })
    }

    private fun handleUserSession(session: UserSession) {
        repository.saveSession(session)
        userSessionSubject.onNext(Result.Success(session))
    }
}