package com.example.bookshelf_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bookshelf_frontend.ui.screens.MainScreen
import com.example.bookshelf_frontend.ui.theme.WiSe24AppLabBookshelfAppFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WiSe24AppLabBookshelfAppFrontendTheme {
                MainScreen()
            }
        }
    }
}