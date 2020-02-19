package com.ore.mvvm.data.network

import com.ore.mvvm.data.network.responses.AuthResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            // create OKHTTP Client to handle NetworkConnectionInterceptor
            val okHttpClient = OkHttpClient.Builder()
                // Bear in mind, we cannot create instances of a class in another class, and we need context here
                // So we pass networkConnectionInterceptor as a constructor in our invoke function
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                // okHttpClient added to our Retrofit Builder
                .client(okHttpClient)
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MyApi::class.java)
        }
    }
}