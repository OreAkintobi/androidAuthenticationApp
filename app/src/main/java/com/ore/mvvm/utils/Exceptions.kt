package com.ore.mvvm.utils

import java.io.IOException

// Exception class created to handle Api errors
// will reflect in SafeApiRequest and AuthViewModel classes
class ApiException(message: String) : IOException(message)

// Exception class created to handle no internet status
// will reflect in NetworkConnectionInterceptor, MyApi and AuthViewModel Classes
class NoInternetException(message: String) : IOException(message)