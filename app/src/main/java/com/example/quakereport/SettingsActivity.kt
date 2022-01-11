package com.example.quakereport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.quakereport.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = SettingsActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    class EarthquakePreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_main, rootKey)

            val minMagnitude =
                findPreference<Preference>(getString(R.string.settings_min_magnitude_key))
            val orderBy = findPreference<Preference>(getString(R.string.settings_order_by_key))

            if (minMagnitude != null) bindPreferenceSummaryToValue(minMagnitude)
            if (orderBy != null) bindPreferenceSummaryToValue(orderBy)
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.setOnPreferenceChangeListener { preference, newValue ->
                val stringValue = newValue.toString()

                if (preference is ListPreference) {
                    val listPreference: ListPreference = preference
                    val prefIndex = listPreference.findIndexOfValue(stringValue)

                    if (prefIndex > 0) {
                        val labels = listPreference.entries
                        preference.summary = labels[prefIndex]
                    }
                } else {
                    preference.summary = stringValue
                }

                true
            }

            val preferences = PreferenceManager.getDefaultSharedPreferences(preference.context)
            val preferencesString = preferences.getString(preference.key, "")

            preference.callChangeListener(preferencesString)
        }
    }
}