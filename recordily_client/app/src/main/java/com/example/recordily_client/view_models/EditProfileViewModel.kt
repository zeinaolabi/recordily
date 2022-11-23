package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditProfileViewModel: ViewModel() {
    private val userService = UserService()

    private val userInfoResult = MutableLiveData<UserResponse>()
    val userInfoResultLiveData : LiveData<UserResponse>
        get() = userInfoResult

    fun getUserInfo(token: String) {
        viewModelScope.launch {
            userInfoResult.postValue(userService.getInfo(token))
        }
    }

    suspend fun editProfile(token: String, name: String, bio: String, image: MultipartBody.Part?): Boolean {
        return try {
            userService.editProfile(token, name, bio, image)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}