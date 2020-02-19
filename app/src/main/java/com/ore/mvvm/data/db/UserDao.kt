package com.ore.mvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ore.mvvm.data.db.entities.CURRENT_USER_ID
import com.ore.mvvm.data.db.entities.User

@Dao
// Data Access Object (DAO) handles the database operations for the User table
interface UserDao {

    // function to insert or update user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // upsert function is suspend to avoid crashes
    suspend fun upsert(user: User): Long

    // function to get user
    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>

}