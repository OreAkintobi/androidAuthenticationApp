package com.ore.mvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// We only want to store the current user in our DB, so we initialize current user and set it to 0
// With NO AUTO INCREMENT. There can be only one user in the local database
const val CURRENT_USER_ID = 0

// User class is defined to map the values in the API login response
@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var email_verified_at: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null
) {
    // Primary key defined to store the current user
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}