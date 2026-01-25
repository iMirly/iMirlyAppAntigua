package com.example.imirly.ui.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imirly.data.local.SessionStore
import com.example.imirly.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatosPersonalesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val sessionStore = SessionStore(application)

    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _profile.value = sessionStore.getUserProfile()
        }
    }

    fun saveProfile(profile: UserProfile, onDone: () -> Unit) {
        viewModelScope.launch {
            sessionStore.saveUserProfile(profile)
            loadProfile()
            onDone()
        }
    }
}
