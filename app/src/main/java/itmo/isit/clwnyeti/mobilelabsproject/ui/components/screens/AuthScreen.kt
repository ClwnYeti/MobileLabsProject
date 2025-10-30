package itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.AuthViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.HeaderViewModel
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.auth.AuthForm
import itmo.isit.clwnyeti.mobilelabsproject.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavController,
    headerVM: HeaderViewModel
) {
    val authVM: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(context.getString(R.string.auth_title)) {
        headerVM.setTitle(context.getString(R.string.auth_title))
    }

    val scope = rememberCoroutineScope()
    AuthForm(
        modifier = Modifier
            .fillMaxSize(),
        authVM.loginFormInput.username,
        authVM.loginFormInput.password,
        authVM.error
    ) {
        scope.launch {
            if (authVM.loginClick()) {
                goToGate(navController)
            }
        }
    }
}

fun goToGate(
    navController: NavController
) {
    navController.navigate(Screen.Gate.route)
}