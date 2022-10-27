package com.example.recordily_client.viewModels

import androidx.lifecycle.ViewModel
import com.example.recordily_client.repositories.RegistrationRepository
import com.example.recordily_client.requests.RegistrationRequest

class RegistrationViewModel: ViewModel() {

    private val repository = RegistrationRepository()

    suspend fun register(registrationRequest: RegistrationRequest): Boolean {
        return try {
            repository.register(registrationRequest)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}