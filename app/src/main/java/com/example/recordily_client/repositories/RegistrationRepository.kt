package com.example.recordily_client.repositories

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.responses.RegistrationResponse

class RegistrationRepository {
    suspend fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        return RetrofitInstance.authClient.register(registrationRequest)
    }
}