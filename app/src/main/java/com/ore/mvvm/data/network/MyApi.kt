package com.ore.mvvm.data.network

import com.ore.mvvm.data.network.responses.AuthResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// Createed MyApi which will use Retrofit and manage ALL INTERNET CALLS
interface MyApi {

    // this annotation is needed for POST in Retrofit
    @FormUrlEncoded
    // defines POST behaviour for login endpoint of RESTful API created
    @POST("login")
    // defines function userLogin for handling behavior from Login fields
    // We cannot directly call this function from our LoginActivity. It will be called from our AuthViewModel,
    // AuthViewModel will interact with our UserRepository
    // function is suspending because logging in the user is a network operation,
    // It might take time, and the function must complete without blocking the entire app
    suspend fun userLogin(
        // field annotation names must match with API calls
        @Field("email") email: String,
        @Field("password") password: String
        // Defined a Retrofit Call with a type of ResponseBody,
        // Now defines a Retrofit Response that takes in  data that is of type AuthResponse from our data class AuthResponse
    ): Response<AuthResponse>

    //
    companion object {
        // this operator function (function that overloads operators) allows us to call/"invoke" the Api when needed
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
            // returns MyApi in Retrofit's .create
        ): MyApi {

            // create OKHTTP Client to handle NetworkConnectionInterceptor
            val okHttpClient = OkHttpClient.Builder()
                // Bear in mind, we cannot create instances of a class in another class, and we need context here
                // So we pass networkConnectionInterceptor as a constructor in our invoke function
                .addInterceptor(networkConnectionInterceptor)
                .build()

            // Retrofit Builder return statement to call MyApi
            return Retrofit.Builder()
                // okHttpClient added to our Retrofit Builder
                .client(okHttpClient)
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                // creates API instance for MyApi return
                .build().create(MyApi::class.java)
        }
    }
}