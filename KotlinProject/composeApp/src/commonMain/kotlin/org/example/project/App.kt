package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.ui.login.LoginScreen
import org.example.project.ui.register.RegisterScreen
import org.example.project.ui.home.HomeScreen

enum class AppScreen {
    LOGIN,
    REGISTER,
    HOME
}

@Composable
fun App() {
    MaterialTheme {
        // We wrap the entire navigation in a Surface with safeDrawingPadding.
        // This ensures NO screen ever overlaps with the status bar or navigation bar.
        Surface(
            modifier = Modifier.fillMaxSize().safeDrawingPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            var currentScreen by remember { mutableStateOf(AppScreen.LOGIN) }

            when (currentScreen) {
                AppScreen.LOGIN -> {
                    LoginScreen(
                        onLoginSuccess = { currentScreen = AppScreen.HOME },
                        onNavigateToSignUp = { currentScreen = AppScreen.REGISTER },
                        onPinterestConnect = { currentScreen = AppScreen.HOME }
                    )
                }
                AppScreen.REGISTER -> {
                    RegisterScreen(
                        onRegisterSuccess = { currentScreen = AppScreen.HOME },
                        onNavigateBackToLogin = { currentScreen = AppScreen.LOGIN }
                    )
                }
                AppScreen.HOME -> {
                    HomeScreen(
                        onLogout = {
                            // Resets the navigation state back to the login screen
                            currentScreen = AppScreen.LOGIN
                        }
                    )
                }
            }
        }
    }
}