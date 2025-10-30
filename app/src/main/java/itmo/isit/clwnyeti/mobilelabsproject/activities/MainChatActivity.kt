package itmo.isit.clwnyeti.mobilelabsproject.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.HeaderViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.utils.AppEvent
import itmo.isit.clwnyeti.mobilelabsproject.logic.network.utils.AppEventBus
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.ChannelRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.MessageRepository
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.common.HeaderUI
import itmo.isit.clwnyeti.mobilelabsproject.ui.navigation.ChatNavGraph
import itmo.isit.clwnyeti.mobilelabsproject.ui.navigation.Screen
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.MobileLabsProjectTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainChatActivity : ComponentActivity() {
    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var messageRepository: MessageRepository

    @Inject
    lateinit var channelRepository: ChannelRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val headerVM: HeaderViewModel = hiltViewModel()

            val headerUI by headerVM.ui.collectAsState()

            LaunchedEffect(Unit) {
                AppEventBus.events.collect { event ->
                    when (event) {
                        is AppEvent.Unauthorized -> {
                            goToGateWithLogout(headerVM, navController)
                        }
                        is AppEvent.Error -> {
                            Log.e("APP", "Ошибка: ${event.message}")
                        }
                    }
                }
            }

            MobileLabsProjectTheme {
                Scaffold(
                    topBar = {
                        HeaderUI(
                            state = headerUI,
                            onLogoutClick = {
                                goToGateWithLogout(headerVM, navController)
                            }
                        )
                    }
                ) { padding ->
                    Box(Modifier.padding(padding)) {
                        ChatNavGraph(navController, headerVM, tokenRepository)
                    }
                }
            }
        }
    }
}

fun goToGateWithLogout(headerVM: HeaderViewModel, navController: NavHostController) {
    headerVM.logout()
    navController.navigate(Screen.Gate.route) {
        popUpTo(0)
        launchSingleTop = true
    }
}