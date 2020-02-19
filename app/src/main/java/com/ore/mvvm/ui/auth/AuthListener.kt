package com.ore.mvvm.ui.auth

import com.ore.mvvm.data.db.entities.User

// AuthListener interface creates callbacks from the AuthViewModel to our Login Activity to display error/success messages
// AuthListener is implemented in Login Activity
interface AuthListener {

    // logging in is takes time, so onStarted will be called to display a progress bar to the user
    fun onStarted()

    // this is called when authentication is successful
    fun onSuccess(user: User)

    //this is called when authentication fails
    fun onFailure(message: String)
}