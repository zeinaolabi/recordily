package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.responses.*
import okhttp3.MultipartBody

class UserService {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return RetrofitInstance.authAPI.login(loginRequest)
    }

    suspend fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        return RetrofitInstance.authAPI.register(registrationRequest)
    }

    suspend fun forgotPassword(email: String): String {
        return RetrofitInstance.authAPI.forgotPassword(email)
    }

    suspend fun getInfo(token: String): UserResponse {
        return RetrofitInstance.userAPI.getInfo(token)
    }

    suspend fun editProfile(token: String, name: String, bio: String, image: MultipartBody.Part?) {
        return RetrofitInstance.userAPI.editProfile(token, name, bio, image)
    }

    suspend fun getTopSongs(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.userAPI.getTopSongs(token, limit)
    }

    suspend fun getRecentlyPlayed(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.userAPI.getRecentlyPlayed(token, limit)
    }

    suspend fun getAlbums(token: String): List<AlbumResponse> {
        return RetrofitInstance.userAPI.getAlbums(token)
    }

    suspend fun getUserSongs(token: String): List<SongResponse> {
        return RetrofitInstance.userAPI.getUserSongs(token)
    }

    suspend fun searchReleasedSongs(token: String, input: String): List<SongResponse> {
        return RetrofitInstance.userAPI.searchReleasedSongs(token, input)
    }
}