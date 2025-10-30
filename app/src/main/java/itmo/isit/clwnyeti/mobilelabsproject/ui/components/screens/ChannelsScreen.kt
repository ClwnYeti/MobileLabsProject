package itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.ChannelViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.HeaderViewModel
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.channels.ChannelRow
import itmo.isit.clwnyeti.mobilelabsproject.ui.navigation.Screen

@Composable
fun ChannelsScreen(
    navController: NavController,
    headerVM: HeaderViewModel
) {
    val channelsViewModel: ChannelViewModel = hiltViewModel()
    val context = LocalContext.current
    val state by channelsViewModel.ui.collectAsState()
    LaunchedEffect(context.getString(R.string.channels_title)) {
        headerVM.setTitle(context.getString(R.string.channels_title))
    }

    when {
        state.items.isEmpty() -> {
            Text(
                text = context.getString(R.string.empty_channels),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(state.items) { ch ->
                    ChannelRow(
                        item = ch,
                        onClick = {
                            navController.navigate(Screen.Messages.routeFor(ch))
                        }
                    )
                }
            }
        }
    }
}
