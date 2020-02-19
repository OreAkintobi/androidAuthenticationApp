package com.ore.mvvm

import android.app.Application
import com.ore.mvvm.data.db.AppDatabase
import com.ore.mvvm.data.network.MyApi
import com.ore.mvvm.data.network.NetworkConnectionInterceptor
import com.ore.mvvm.data.repositories.UserRepository
import com.ore.mvvm.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

// This is the base class for our application, usually instantiated before anything else
class MyApplication : Application(), KodeinAware {
    // overwrites Kodein binding method within which we declare our dependencies
    override val kodein = Kodein.lazy {

        // binds all the objects that we will need within the application
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
    }
}