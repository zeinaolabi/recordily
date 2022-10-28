package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import com.example.recordily_client.repositories.LoginRepository
import com.example.recordily_client.requests.LoginRequest

class LoginViewModel: ViewModel() {

    private val repository = LoginRepository()

    suspend fun login(loginRequest: LoginRequest): Boolean {
        return try {
            repository.login(loginRequest)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}