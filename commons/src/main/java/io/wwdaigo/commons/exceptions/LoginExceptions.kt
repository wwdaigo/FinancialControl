package io.wwdaigo.commons.exceptions

import java.lang.Exception

class LoginInvalidCredentials: Exception()
class LoginFacebookInvalidAuthToken: Exception()
class LoginGoogleInvalidAuthToken: Exception()