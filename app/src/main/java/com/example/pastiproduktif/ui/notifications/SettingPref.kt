package com.example.pastiproduktif.ui.notifications

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.pastiproduktif.R

internal class SettingPref: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var keyNotification: String
    private lateinit var switch: SwitchPreference
    private lateinit var broadcastReceiver: NotificationReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_pref)
        init()
        setSummary()
        broadcastReceiver = NotificationReceiver()

        if(switch.isChecked){
            context?.let { broadcastReceiver.setNotify(it, "09:00","Don't Forget to write your activity")}
                Toast.makeText(context, "Remainder actives", Toast.LENGTH_SHORT).show()
        } else
            context?.let { broadcastReceiver.cancelNotification(it)}
                Toast.makeText(context, "Remainder inactive", Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        keyNotification = resources.getString(R.string.key_switch)
        switch = findPreference<SwitchPreference>(keyNotification) as SwitchPreference
    }

    private fun setSummary() {
        val sharedPreference = preferenceManager.sharedPreferences
        switch.isChecked = sharedPreference.getBoolean(keyNotification, false)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == keyNotification){
            switch.isEnabled = sharedPreferences.getBoolean(keyNotification, false)
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}