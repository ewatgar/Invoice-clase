package com.murray.invoice.ui.preferences

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.Locator
import com.murray.invoice.R

class AccountFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.account_preferences, rootKey)
        initPreferencesEmail()
        initPreferencesPassword()
        val store = Locator.settingsPreferencesRepository
        preferenceManager.preferenceDataStore = store
    }

    private fun initPreferencesEmail() {
        val email =
            preferenceManager.findPreference<EditTextPreference>(getString(R.string.key_email))
        email?.setOnBindEditTextListener {
            Locator.userPreferencesRepository.getEmail()
            it.setText("informatica@iesportada.org")
            it.isEnabled = true
        }
    }

    private fun initPreferencesPassword() {
        val password =
            preferenceManager.findPreference<EditTextPreference>(getString(R.string.key_password))
        password?.setOnBindEditTextListener {
            Locator.userPreferencesRepository.getPassword()
            it.setText("12345678")
            it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            it.selectAll()
        }
        password?.setOnPreferenceChangeListener { _, newPassword ->
            Locator.userPreferencesRepository.savePassword(newPassword as String)
            true
        }
    }
}
