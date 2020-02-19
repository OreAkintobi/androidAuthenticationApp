package com.ore.mvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.ore.mvvm.data.repositories.UserRepository
import com.ore.mvvm.utils.ApiException
import com.ore.mvvm.utils.Coroutines
import com.ore.mvvm.utils.NoInternetException

// Used for authenticating user data in Login and Signup Activities
//AuthViewModel is passed in as the data binding Data Variable for the xml layouts for both LOGIN & SIGNUP
class AuthViewModel(
    // injects our UserRepository as a dependency in our AuthViewModel
    private val userRepository: UserRepository
) : ViewModel() {

    // defines string variables for email and password with null initial values and safety operators
    var email: String? = null
    var password: String? = null
    // AuthListener is defined here to implement callback functions onStarted, onSuccess, onFailure
    var authListener: AuthListener? = null

    // gets logged in user from our Database via Repository to observe in the LoginActivity
    fun getLoggedInUser() = userRepository.getUser()

    // defines behavior for login button click and passes view parameter to be able to pass it in the xml file
    fun onLoginButtonClick(view: View) {
        // AuthListener: onStarted
        authListener?.onStarted()
        // handles empty or null email and/or password fields
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            // AuthListener: onFailure
            authListener?.onFailure("invalid email or password")
            return
        }
        // AuthListener: onSuccess
        // Calls our response from the 'main' function that was defined in our Coroutines Object
        Coroutines.main {
            try {
                val authResponse = userRepository.userLogin(email!!, password!!)
                // the authListener checks the authResponse (previously loginResponse), and communicates the result and behavior for each response
                authResponse.user?.let {
                    // if successful, run onSuccess method of AuthListener,
                    authListener?.onSuccess(it)
                    // and save User to Database
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