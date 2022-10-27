package com.example.recordily_client.viewModels

import androidx.lifecycle.ViewModel
import com.example.recordily_client.repositories.UserRepository
import com.example.recordily_client.requests.LoginRequest

class UserViewModel: ViewModel() {

    private val repository = UserRepository()

    suspend fun login(loginRequest: LoginRequest): Boolean {
        return try {
            repository.login(loginRequest)
            true
        } catch (exception: Throwable) {
            false
        }
    }


}