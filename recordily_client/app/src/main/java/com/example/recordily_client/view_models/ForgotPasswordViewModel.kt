package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import com.example.recordily_client.services.UserService

class ForgotPasswordViewModel: ViewModel() {

    private val service = UserService()

    suspend fun resetPassword(email: String): Boolean {
        return try {
            service.forgotPassword(email)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}