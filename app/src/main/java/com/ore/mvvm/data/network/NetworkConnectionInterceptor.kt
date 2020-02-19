package com.ore.mvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.ore.mvvm.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

// Interceptor class to handle login/signup when there is no internet access
// This will be added into the retrofit object in MyApi Class
class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    // intercept function needs to override to intercept network calls
    override fun intercept(chain: Interceptor.Chain): Response {
        // If internet is not available
        if (!isInternetAvailable()) {
            // throw exception message from Exceptions class
            throw NoInternetException("make sure you have an active internet connection")
        }
        // if network is available, this will happen
        return chain.proceed(chain.request())
    }

    // function to check if internet is available. Context passed as parameter in parent class to be used here
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}