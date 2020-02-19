package com.ore.mvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.ore.mvvm.data.repositories.UserRepository
import com.ore.mvvm.utils.ApiException
import com.ore.mvvm.utils.Coroutines
import com.ore.mvvm.utils.NoInternetException

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = userRepository.getUser()

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            // failure
            authListener?.onFailure("invalid email or password")
            return
        }
        // success
        Coroutines.main {
            try {
                val authResponse = userRepository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }
            // catching ApiException error from Exceptions class
            catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }
            // catching NoInternetException error from Exceptions class
            catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}