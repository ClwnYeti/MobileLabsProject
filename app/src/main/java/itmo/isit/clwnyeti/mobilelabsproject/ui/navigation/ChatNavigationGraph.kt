package itmo.isit.clwnyeti.mobilelabsproject.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.ChannelInfo
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageImageData
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.HeaderViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens.AuthScreen
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens.ChannelsScreen
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens.GateScreen
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens.ImageScreen
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens.MessagesScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Channels : Screen("channels")
    data object Gate : Screen("gate")
    data object Messages : Screen("messages?channel={channel}") {
        fun routeFor(channel: ChannelInfo): String {
            val json = Uri.encode(Json.encodeToString(channel))
            return "messages?channel=$json"
        }
    }
    data object Image : Screen("images?image={imageData}") {
        fun routeFor(imageData: MessageImageData): String {
            val json = Uri.encode(Json.encodeToString(imageData))
            return "images?image=$json"
        }
    }
}

@Composable
fun ChatNavGraph(navController: NavHostController,
                 headerVM: HeaderViewModel,
                 tokenStore: TokenRepository
) {
    NavHost(navController, startDestination = Screen.Gate.route) {
        composable(Screen.Gate.route) {
            GateScreen(navController, tokenStore)
        }
        composable(Screen.Login.route) { AuthScreen(navController, headerVM) }
        composable(Screen.Channels.route) { ChannelsScreen(navController, headerVM) }
        composable(Screen.Messages.route) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("channel")
            val channelData = json?.let { Json.decodeFromString<ChannelInfo>(Uri.decode(it)) }
            MessagesScreen(navController, headerVM, channelData)
        }
        composable(Screen.Image.route) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("imageData")
            val imageData = json?.let { Json.decodeFromString<MessageImageData>(Uri.decode(it)) }
            ImageScreen(navController, headerVM, imageData)
        }
    }
}