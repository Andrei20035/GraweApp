package com.example.webshoptest.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class SecureTokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs by lazy {
        EncryptedSharedPreferences.create(
            context,
            "token_prefs",
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveToken(token: String) {
        prefs.edit { putString("jwt", token) }
    }

    fun getToken(): String? {
        return prefs.getString("jwt", null)
    }
}