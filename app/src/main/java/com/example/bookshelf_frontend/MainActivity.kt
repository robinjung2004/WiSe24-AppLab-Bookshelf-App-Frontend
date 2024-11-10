package com.example.bookshelf_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf_frontend.ui.screens.MainScreen
import com.example.bookshelf_frontend.ui.screens.settings.SettingsViewModel
import com.example.bookshelf_frontend.ui.theme.WiSe24AppLabBookshelfAppFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            WiSe24AppLabBookshelfAppFrontendTheme(
                darkTheme = isDarkMode
            ) {
                MainScreen()
            }
        }
    }
}