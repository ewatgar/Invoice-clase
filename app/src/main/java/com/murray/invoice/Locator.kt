package com.murray.invoice

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.murray.invoice.data.preferences.DataStorePreferencesRepository
import com.murray.invoice.data.preferences.UserPreferencesRepository

object Locator {
    private var application:Application? = null
    //inline -> variable se inicializa cuando se llama el getter
    private inline val requireApplication get() = application ?: error("Mssing call:  initWith(application)")

    fun initWith(application: Application){
        this.application = application
    }

    private val Context.userStore by preferencesDataStore(name="user") //se añadirá la palabra "preferences" al final automáticamente
    private val Context.settingStore by preferencesDataStore(name="settings") //se añadirá la palabra "preferences" al final automáticamente

    //lazy es igual que lateinit (inicialización tardía)
    val userPreferencesRepository by lazy{
        UserPreferencesRepository(requireApplication.userStore)
    }

    val settingsPreferencesRepository by lazy {
        DataStorePreferencesRepository(requireApplication.settingStore)
    }

}