package com.example.recordily_client.validation

import android.text.TextUtils

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
}

fun isValidPassword(password: String): Boolean {
    return !TextUtils.isEmpty(password) && password.length != 6
}