package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val service = UserService()

    private val userInfoResult = MutableLiveData<UserResponse>()
    val userInfoResultLiveData: LiveData<UserResponse>
        get() = userInfoResult

    fun getUserInfo(token: String){
        viewModelScope.launch {
            userInfoResult.postValue(service.getUserInfo(token))
        }
    }
}