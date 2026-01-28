package com.example.imirly.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var city by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun reset() {
        firstName = ""
        lastName = ""
        city = ""
        email = ""
        password = ""
    }
}
