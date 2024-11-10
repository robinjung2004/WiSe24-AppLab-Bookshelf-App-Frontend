package com.example.bookshelf_frontend.ui.screens.settings

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.bookshelf_frontend.util.NotificationHelper

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationHelper = NotificationHelper(application)

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun toggleNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        if (enabled) {
            notificationHelper.showDemoNotification()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val activity = getApplication<Application>().applicationContext
            if (ContextCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                // Hier sollten Sie die Berechtigung anfordern
                // Dies erfordert Activity-Context und sollte idealerweise
                // über einen Event-Channel zum UI gehandhabt werden
            }
        }
    }
}