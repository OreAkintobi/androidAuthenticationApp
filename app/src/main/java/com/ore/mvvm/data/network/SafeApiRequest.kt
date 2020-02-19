package com.ore.mvvm.data.network

import com.ore.mvvm.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

// All errors from API calls will be handled here
abstract class SafeApiRequest {

    // this generic network suspend function performs our API request and returns the Response directly
    // this function takes a call parameter which is actually another suspend function
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            // gets the response errorBody if the response is not successful
            val error = response.errorBody()?.string()
            val message = StringBuilder()

            error?.let {
                try {
                    // if we are getting the error response defined in our API code, we can parse the JSON response
                    message.append(JSONObject(it).getString("message"))
                }
                // if we have a different error, we might not get a JSON response
                catch (e: JSONException) {
                }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")

            // throws Exception handled by our ApiException custom class in Exceptions file
            throw ApiException(message.toString())
        }
    }
}