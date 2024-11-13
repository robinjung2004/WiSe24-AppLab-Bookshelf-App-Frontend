package com.example.bookshelf_frontend

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf_frontend.ui.screens.MainScreen
import com.example.bookshelf_frontend.ui.screens.settings.SettingsViewModel
import com.example.bookshelf_frontend.ui.theme.WiSe24AppLabBookshelfAppFrontendTheme

class MainActivity : ComponentActivity() {
    private lateinit var settingsViewModel: SettingsViewModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        settingsViewModel.onNotificationPermissionResult(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        settingsViewModel.setActivity(this)

        enableEdgeToEdge()
        setContent {
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            WiSe24AppLabBookshelfAppFrontendTheme(
                darkTheme = isDarkMode
            ) {
                MainScreen()
            }
        }
    }

    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}