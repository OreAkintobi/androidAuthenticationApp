package com.ore.mvvm.data.repositories

import com.ore.mvvm.data.db.AppDatabase
import com.ore.mvvm.data.db.entities.User
import com.ore.mvvm.data.network.MyApi
import com.ore.mvvm.data.network.SafeApiRequest
import com.ore.mvvm.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val database: AppDatabase
) : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) {
        database.getUserDao().upsert(
            user
        )
    }

    fun getUser() = database.getUserDao().getUser()
}