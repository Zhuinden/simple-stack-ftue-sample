package com.zhuinden.simplestackftuesample.app

import android.content.SharedPreferences

class AuthenticationManager(
    private val sharedPreferences: SharedPreferences
) {
    fun isAuthenticated(): Boolean {
        return sharedPreferences.getString("username", "")!!.isNotEmpty()
    }

    fun saveRegistration(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    fun clearRegistration() {
        sharedPreferences.edit().remove("username").apply()
    }

    fun getAuthenticatedUser(): String {
        return checkNotNull(
            sharedPreferences.getString(
                "username",
                ""
            ).takeIf { it!!.isNotEmpty() })
    }
}