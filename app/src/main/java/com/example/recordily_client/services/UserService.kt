package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.apis.UserAPI
import com.example.recordily_client.data.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
     fun login(email: String, password: String, onResult: (UserData?) -> Unit){
        val retrofit = RetrofitInstance.retrofit.create(UserAPI::class.java)
        retrofit.login(email, password).enqueue(
            object : Callback<UserData> {
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<UserData>, response: Response<UserData>
                ) {
                    val authResponse = response.body()
                    onResult(authResponse)
                }
            }
        )
    }
}