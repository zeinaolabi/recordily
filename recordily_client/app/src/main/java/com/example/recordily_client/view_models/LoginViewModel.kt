package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import com.example.recordily_client.requests.LoginRequest
import androidx.lifecycle.AndroidViewModel
import com.example.recordily_client.services.UserService
import com.example.recordily_client.validation.UserCredentials

@SuppressLint("StaticFieldLeak")
class LoginViewModel(application: Application): AndroidViewModel(application){
    private val userCredentials = UserCredentials(application)
    private val service = UserService()

    @SuppressLint("CommitPrefEdits")
    suspend fun login(loginRequest: LoginRequest): Boolean {
        return try {
            val response = service.login(loginRequest)
            userCredentials.addCredentials(response.id, response.token, response.user_type_id)

            true
        } catch (exception: Throwable) {
            false
        }
    }
}