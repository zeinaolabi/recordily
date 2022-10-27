package com.example.recordily_client.viewModels

import com.example.recordily_client.repositories.LoginRepository
import com.example.recordily_client.repositories.RegistrationRepository
import com.example.recordily_client.requests.RegistrationRequest

class RegistrationViewModel {

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