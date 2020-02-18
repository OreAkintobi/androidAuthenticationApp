package com.ore.mvvm.data.network.responses

import com.ore.mvvm.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)