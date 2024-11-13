package com.example.bookshelf_frontend.ui.screens.settings

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.bookshelf_frontend.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.bookshelf_frontend.util.NotificationHelper
import java.lang.ref.WeakReference

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationHelper = NotificationHelper(application)
    private var activityReference: WeakReference<MainActivity>? = null

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun setActivity(activity: MainActivity) {
        activityReference = WeakReference(activity)
    }

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun toggleNotifications(enabled: Boolean) {
        if (enabled) {
            requestNotificationPermission()
        } else {
            _notificationsEnabled.value = false
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val activity = getApplication<Application>().applicationContext
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                activityReference?.get()?.requestNotificationPermission()
            } else {
                enableNotifications()
            }
        } else {
            enableNotifications()
        }
    }

    fun onNotificationPermissionResult(granted: Boolean) {
        if (granted) {
            enableNotifications()
        } else {
            _notificationsEnabled.value = false
        }
    }

    private fun enableNotifications() {
        _notificationsEnabled.value = true
        notificationHelper.showDemoNotification()
    }
}