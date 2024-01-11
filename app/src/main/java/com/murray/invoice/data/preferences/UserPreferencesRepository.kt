package com.murray.invoice.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Esta clase contiene todos los métodos necesarios para leer y guardar datos del usuario,
 * preferencias en el almacen de datos "user_preferences"
 */
class UserPreferencesRepository (private val dataStore: DataStore<Preferences>) {
    /**
     * Método para guardar los user_preferences
     */
    suspend fun saveUser(email: String, password: String, id: Int) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email ?: "none"
            preferences[PASSWORD] = password ?: "none"
            preferences[ID] = id ?: 0
        }
    }

    companion object{
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val ID = intPreferencesKey("id")
    }

}