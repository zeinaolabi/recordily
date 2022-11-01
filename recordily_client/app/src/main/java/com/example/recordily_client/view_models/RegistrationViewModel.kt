package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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