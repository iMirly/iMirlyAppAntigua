package com.example.imirly.data.local

import android.content.Context

data class UserData(
    val nombre: String,
    val email: String,
    val telefono: String,
    val provincia: String
)

class UserStore(context: Context) {

    private val prefs =
        context.getSharedPreferences("user_store", Context.MODE_PRIVATE)

    fun getUser(): UserData =
        UserData(
            nombre = prefs.getString("nombre", "Nombre usuario") ?: "",
            email = prefs.getString("email", "usuario@email.com") ?: "",
            telefono = prefs.getString("telefono", "600 000 000") ?: "",
            provincia = prefs.getString("provincia", "Madrid") ?: ""
        )

    fun saveUser(user: UserData) {
        prefs.edit()
            .putString("nombre", user.nombre)
            .putString("email", user.email)
            .putString("telefono", user.telefono)
            .putString("provincia", user.provincia)
            .apply()
    }

    fun getPassword(): String =
        prefs.getString("password", "123456") ?: "123456"

    fun savePassword(newPassword: String) {
        prefs.edit()
            .putString("password", newPassword)
            .apply()
    }

}
