package com.raksha.kavach.data

import android.content.Context

data class AuthUser(
    val name: String,
    val email: String
)

class AuthRepository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun register(name: String, email: String, password: String): Pair<Boolean, String?> {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return Pair(false, "Fill all fields")
        }

        prefs.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_LOGGED_IN, true)
            .apply()

        return Pair(true, null)
    }

    fun login(email: String, password: String): Pair<Boolean, String?> {
        val savedEmail = prefs.getString(KEY_EMAIL, null)
        val savedPassword = prefs.getString(KEY_PASSWORD, null)

        if (savedEmail == null || savedPassword == null) {
            return Pair(false, "No local account. Create one or use Demo Mode.")
        }

        if (email == savedEmail && password == savedPassword) {
            prefs.edit().putBoolean(KEY_LOGGED_IN, true).apply()
            return Pair(true, null)
        }

        return Pair(false, "Invalid email or password")
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    fun startDemoSession() {
        prefs.edit()
            .putString(KEY_NAME, "Demo Worker")
            .putString(KEY_EMAIL, "demo@raksha.local")
            .putString(KEY_PASSWORD, "demo")
            .putBoolean(KEY_LOGGED_IN, true)
            .apply()
    }

    fun getUser(): AuthUser? {
        val name = prefs.getString(KEY_NAME, null)
        val email = prefs.getString(KEY_EMAIL, null)
        if (name == null || email == null) return null
        return AuthUser(name, email)
    }

    fun logout() {
        prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply()
    }

    companion object {
        private const val PREFS = "raksha_auth"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_LOGGED_IN = "logged_in"
    }
}
