package com.ore.mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ore.mvvm.data.db.entities.User

// Creates Database Class to store User data (for now) and defines entities
// Database cannot be directly accessed in the Login/Signup activity. It must be accessed from the repositary,
// With the use of the DAOs
@Database(entities = [User::class], version = 1)
// Database must always be can abstract class that extends RoomDatabase
// AppDatabase gives us access to getUserDao which helps us to save the current user to our database
abstract class AppDatabase : RoomDatabase() {

    // abstract functions must be created for ALL DAOs
    abstract fun getUserDao(): UserDao

    // companion object is responsible for ACTUALLY creating the database
    companion object {

        // 'Volatile' means the instance variable is immediately visible to ALL other threads
        @Volatile
        private var instance: AppDatabase? = null
        // LOCK is created to ensure that we do not create 2 instances of our database
        private val LOCK = Any()

        // invoke function takes context as parameter since context is needed to create database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyDatabase.db"
        ).build()
    }
}