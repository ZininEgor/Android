package com.example.timer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import java.util.*
import com.example.timer.others.Constants.ACTION_SHOW_TIMER_FRAGMENT
import com.example.timer.timer.MyService

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val themeId = when (preference.getString("theme", "Light")) {
            "Night" -> R.style.DarkTheme
            "Light" -> R.style.Base_Theme_AppCompat_Light
            "Purple" -> R.style.DarkPurpleTheme

            else -> R.style.LightTheme
        }
        this.theme?.applyStyle(themeId, true)
        val locale = Locale(preference.getString("locale", "Eng"))

        val fontId = when (preference.getString("font", "large")) {
            "small" -> R.style.FontStyle_Small
            "medium" -> R.style.FontStyle_Medium
            else -> R.style.FontStyle_Medium
        }
        this.theme?.applyStyle(fontId, true)


        val config = resources.configuration
        if (locale.language != config.locale.language) {
            application.resources.configuration.locale = locale
            config.locale = locale;
            application.resources.updateConfiguration(
                application.resources.configuration,
                application.resources.displayMetrics
            )
            resources.updateConfiguration(config, resources.displayMetrics)
        }
        setContentView(R.layout.main_activity)
        navigateToTimerFragmentIfNeeded(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTimerFragmentIfNeeded(intent)
    }

    private fun navigateToTimerFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TIMER_FRAGMENT) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_timerFragment, bundleOf("sleepNightKey" to MyService.key.value))
        }

    }

}

