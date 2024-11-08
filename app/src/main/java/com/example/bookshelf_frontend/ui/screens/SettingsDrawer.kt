package com.example.bookshelf_frontend.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsDrawer(
    drawerState: DrawerState,
    notificationsEnabled: Boolean,
    onNotificationChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Settings",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = notificationsEnabled,
                        onCheckedChange = onNotificationChange
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            "Enable Notifications",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "Get notified about new books",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    ) {
        content()
    }
}