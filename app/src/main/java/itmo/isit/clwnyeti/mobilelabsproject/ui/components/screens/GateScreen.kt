package itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import itmo.isit.clwnyeti.mobilelabsproject.ui.navigation.Screen

@Composable
fun GateScreen(nav: NavController, tokenStore: TokenRepository) {
    LaunchedEffect(Unit) {
        val token = tokenStore.getToken()
        if (token.isNullOrBlank()) {
            nav.navigate(Screen.Login.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        } else {
            nav.navigate(Screen.Channels.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}