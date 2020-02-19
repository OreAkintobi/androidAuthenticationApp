package com.ore.mvvm.data.repositories

import com.ore.mvvm.data.db.AppDatabase
import com.ore.mvvm.data.db.entities.User
import com.ore.mvvm.data.network.MyApi
import com.ore.mvvm.data.network.SafeApiRequest
import com.ore.mvvm.data.network.responses.AuthResponse

// We need to interact with UserRepository from our AuthViewModel,
// LoginActivity will interact with AuthViewModel
class UserRepository(
    // injects MyApi and our AppDatabase as dependencies
    private val api: MyApi,
    private val database: AppDatabase
// class extends SafeApiRequest class so that we can use it in our Repository
) : SafeApiRequest() {
    // this function performs the ACTUAL LOGIN
    // function previously returned a LiveData of type String for testing
    // now returns an AuthResponse return type because MyApi no longer returns LiveData
    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    // this function saves the user in the local database. Can only be performed asynchronously
    suspend fun saveUser(user: User) {
        database.getUserDao().upsert(
            user
        )
    }

    fun getUser() = database.getUserDao().getUser()
}