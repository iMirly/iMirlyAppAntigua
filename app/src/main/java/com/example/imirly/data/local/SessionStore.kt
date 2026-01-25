package com.example.imirly.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import com.example.imirly.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "imirly_session"
)

class SessionStore(
    private val context: Context

) {


    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")

        private val EMAIL = stringPreferencesKey("email")
        private val PHONE = stringPreferencesKey("phone")
        private val BIRTHDATE = stringPreferencesKey("birthdate")
        private val ADDRESS = stringPreferencesKey("address")
        private val CITY = stringPreferencesKey("city")
        private val POSTAL_CODE = stringPreferencesKey("postal_code")

        private val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
    }

    val userProfileFlow: Flow<UserProfile> =
        context.dataStore.data.map { prefs ->
            UserProfile(
                name = prefs[USER_NAME] ?: "",
                email = prefs[EMAIL] ?: "",
                phone = prefs[PHONE] ?: "",
                birthdate = prefs[BIRTHDATE] ?: "",
                address = prefs[ADDRESS] ?: "",
                city = prefs[CITY] ?: "",
                postalCode = prefs[POSTAL_CODE] ?: ""
            )
        }

    /* ---------- PERFIL ---------- */

    suspend fun getUserProfile(): UserProfile {
        val prefs = context.dataStore.data.first()
        return UserProfile(
            name = prefs[USER_NAME] ?: "",
            email = prefs[EMAIL] ?: "",
            phone = prefs[PHONE] ?: "",
            birthdate = prefs[BIRTHDATE] ?: "",
            address = prefs[ADDRESS] ?: "",
            city = prefs[CITY] ?: "",
            postalCode = prefs[POSTAL_CODE] ?: ""
        )
    }

    suspend fun saveUserProfile(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = profile.name
            prefs[EMAIL] = profile.email
            prefs[PHONE] = profile.phone
            prefs[BIRTHDATE] = profile.birthdate
            prefs[ADDRESS] = profile.address
            prefs[CITY] = profile.city
            prefs[POSTAL_CODE] = profile.postalCode
        }
    }

    suspend fun saveBasicProfile(
        name: String,
        email: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = name
            prefs[EMAIL] = email
        }
    }



    /* ---------- ONBOARDING ---------- */

    suspend fun setOnboardingDone() {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_DONE] = true
        }
    }

    suspend fun isOnboardingDone(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[ONBOARDING_DONE] ?: false
    }

    /* ---------- SESIÓN ---------- */

    suspend fun login(
        userId: String,
        userName: String,
        email: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = true
            prefs[USER_ID] = userId
            prefs[USER_NAME] = userName
            prefs[EMAIL] = email
        }
    }


    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = false
            prefs.remove(USER_ID)
            prefs.remove(USER_NAME)
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[IS_LOGGED_IN] ?: false
    }

    suspend fun getUserId(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USER_ID]
    }

    suspend fun getUserName(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USER_NAME]
    }

    suspend fun deleteAccount() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

}
