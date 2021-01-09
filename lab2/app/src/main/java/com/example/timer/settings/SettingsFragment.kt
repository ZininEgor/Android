package com.example.timer.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.timer.MainActivity
import com.example.timer.R
import com.example.timer.database.SleepDatabase


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var MainActivity: MainActivity

    @SuppressLint("ResourceType")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        MainActivity = context as MainActivity
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SettingsViewModelFactory(dataSource, application)
        val sleepTrackerViewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        val button: Preference? = findPreference(getString(R.string.drop))
        button!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            sleepTrackerViewModel.onClear()
            true
        }

    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key != "isNight")
            requireActivity().recreate()

    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}