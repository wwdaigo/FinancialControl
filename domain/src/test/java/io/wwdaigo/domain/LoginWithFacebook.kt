package io.wwdaigo.domain

import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.wwdaigo.commons.exceptions.LoginFacebookInvalidAuthToken
import io.wwdaigo.commons.exceptions.LoginInvalidCredentials
import io.wwdaigo.domain.businessrules.impl.LoginImpl
import io.wwdaigo.domain.commons.Result
import io.wwdaigo.domain.entities.AuthMethod
import io.wwdaigo.domain.entities.UserSession
import io.wwdaigo.domain.repositories.AuthRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class LoginWithFacebook {

    private val SESSION_TOKEN = "adajsdioasjdoiasiouaouqrw"
    private val AUTH_TOKEN = "authokokenn"
    private val INVALID_AUTH_TOKEN = "invalidauthokokenn"

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var repository: AuthRepository

    @InjectMocks
    lateinit var loginImpl: LoginImpl

    @Test
    fun loginWithFacebook() {
        doAnswer { val session = UserSession(SESSION_TOKEN, AuthMethod.FACEBOOK)
            Observable.just(session) }
                .whenever(repository).loginWithFacebook(AUTH_TOKEN)

        val test = TestObserver<Result<UserSession>>()
        loginImpl.callback.userSession.subscribe(test)

        loginImpl.loginWithFacebook(AUTH_TOKEN)

        test.assertValue { it is Result.Success }
        test.assertValue { (it as Result.Success).data.token == SESSION_TOKEN }
        test.assertValue { (it as Result.Success).data.authMethod == AuthMethod.FACEBOOK }
        test.assertNever { it is Result.Error }
    }

    @Test
    fun loginWithInvalidCredentials() {
        doAnswer { Observable.error<LoginFacebookInvalidAuthToken>(LoginFacebookInvalidAuthToken()) }
                .whenever(repository).loginWithFacebook(INVALID_AUTH_TOKEN)

        val test = TestObserver<Result<UserSession>>()
        loginImpl.callback.userSession.subscribe(test)

        loginImpl.loginWithFacebook(INVALID_AUTH_TOKEN)

        test.assertValue { it is Result.Error }
        test.assertValue { (it as Result.Error).throwable is LoginFacebookInvalidAuthToken}
        test.assertNever { it is Result.Success }
    }
}