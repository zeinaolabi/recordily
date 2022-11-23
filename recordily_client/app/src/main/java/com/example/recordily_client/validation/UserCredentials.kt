package com.example.recordily_client.validation

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel

@SuppressLint("StaticFieldLeak")
class UserCredentials(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    @SuppressLint("RestrictedApi")
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("login", 0)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun addCredentials(id: Int, token: String, type: Int) {
        editor.clear()
        editor.apply {
            putInt("id", id)
            putString("token", token)
            putInt("user_type_id", type)
        }
        editor.commit()
    }

    fun removeCredentials() {
        editor.remove("id")
        editor.remove("token")
        editor.remove("user_type_id")
        editor.clear()
        editor.commit()
    }

    fun getID(): Int {
        return sharedPreferences.getInt("id", -1)
    }

    fun getToken(): String {
        val token = sharedPreferences.getString("token", "").toString()
        if(token == ""){
            return ""
        }
        return "Bearer $token"
    }

    fun getType(): Int {
        return sharedPreferences.getInt("user_type_id", -1)
    }
}