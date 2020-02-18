package com.ore.mvvm.ui.auth

import com.ore.mvvm.data.db.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(loginResponse: User)
    fun onFailure(message: String)
}