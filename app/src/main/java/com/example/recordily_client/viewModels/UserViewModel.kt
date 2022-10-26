package com.example.recordily_client.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.repositories.UserRepository
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    private val _loginLiveData = MutableLiveData<LoginResponse>()
    val loginLiveData: LiveData<LoginResponse?> = _loginLiveData

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            val response = repository.login(loginRequest)

            _loginLiveData.postValue(response)
        }
    }

}