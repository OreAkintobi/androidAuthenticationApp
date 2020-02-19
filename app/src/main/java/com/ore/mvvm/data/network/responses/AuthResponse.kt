package com.ore.mvvm.data.network.responses

import com.ore.mvvm.data.db.entities.User

// this class will only store a JSON response from the server
data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)