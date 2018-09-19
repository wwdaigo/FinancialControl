package io.wwdaigo.domain

import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
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

class LoginWithEmailTest {

    private val TOKEN = "adajsdioasjdoiasiouaouqrw"
    private val EMAIL_VALID = "valid@email.com"
    private val EMAIL_INVALID = "invalid@email.com"
    private val PASSWORD = "password"

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var repository: AuthRepository

    @InjectMocks
    lateinit var loginImpl: LoginImpl

    @Test
    fun loginWithValidEmailAndPassword() {
        doAnswer { val session = UserSession(TOKEN, AuthMethod.EMAIL)
            Observable.just(session) }
                .whenever(repository).loginWithEmail(EMAIL_VALID, PASSWORD)

        val test = TestObserver<Result<UserSession>>()
        loginImpl.callback.userSession.subscribe(test)

        loginImpl.loginWithEmail(EMAIL_VALID, PASSWORD)

        test.assertValue { it is Result.Success }
        test.assertValue { (it as Result.Success).data.token == TOKEN }
        test.assertValue { (it as Result.Success).data.authMethod == AuthMethod.EMAIL }
        test.assertNever { it is Result.Error }
    }

    @Test
    fun loginWithInvalidCredentials() {
        doAnswer { Observable.error<LoginInvalidCredentials>(LoginInvalidCredentials()) }
                .whenever(repository).loginWithEmail(EMAIL_INVALID, PASSWORD)

        val test = TestObserver<Result<UserSession>>()
        loginImpl.callback.userSession.subscribe(test)

        loginImpl.loginWithEmail(EMAIL_INVALID, PASSWORD)

        test.assertValue { it is Result.Error }
        test.assertValue { (it as Result.Error).throwable is LoginInvalidCredentials}
        test.assertNever { it is Result.Success }
    }
}