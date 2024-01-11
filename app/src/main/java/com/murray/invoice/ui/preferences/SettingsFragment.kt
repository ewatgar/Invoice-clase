package com.murray.invoice.ui.preferences

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
        val accountPreference = preferenceManager.findPreference<Preference>(getString(R.string.key_account))
        accountPreference?.setOnPreferenceClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
            true
        }
    }
}