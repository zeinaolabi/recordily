package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.services.UserService

class RegistrationViewModel: ViewModel() {

    private val userService = UserService()

    suspend fun register(registrationRequest: RegistrationRequest): Boolean {
        return try {
            userService.register(registrationRequest)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}