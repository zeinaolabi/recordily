package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import com.example.recordily_client.repositories.LoginRepository
import com.example.recordily_client.requests.LoginRequest
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel

@SuppressLint("StaticFieldLeak")
class LoginViewModel(application: Application): AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext
    private val repository = LoginRepository()


    @SuppressLint("RestrictedApi")
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("login", 0)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    @SuppressLint("CommitPrefEdits")
    suspend fun login(loginRequest: LoginRequest): Boolean {
        return try {
            val response = repository.login(loginRequest)
            editor.clear()
            editor.apply {
                putInt("id", response.id)
                putString("token", response.token)
                putInt("user_type_id", response.user_type_id)
            }
            editor.commit()

            true
        } catch (exception: Throwable) {
            false
        }
    }

    fun logout(): Boolean {
        return try {
            editor.remove("id")
            editor.remove("token")
            editor.remove("user_type_id")
            editor.clear()
            editor.commit()

            true
        } catch (exception: Throwable) {
            false
        }
    }
}