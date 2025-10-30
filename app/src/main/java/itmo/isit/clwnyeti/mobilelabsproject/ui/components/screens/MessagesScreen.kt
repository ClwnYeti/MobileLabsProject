package itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.ChannelInfo
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Message
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.HeaderViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.MessageViewModel
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.common.TextInput
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.messages.MessageRow
import itmo.isit.clwnyeti.mobilelabsproject.ui.navigation.Screen
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun MessagesScreen(
    navController: NavController,
    headerVM: HeaderViewModel,
    channelInfo: ChannelInfo?
) {
    if (channelInfo == null) {
        navController.popBackStack()
    }
    val messagesViewModel: MessageViewModel = hiltViewModel()
    val headerUI by headerVM.ui.collectAsState()
    val online by messagesViewModel.online.collectAsState()
    val items by messagesViewModel.messages.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(channelInfo!!.name) {
        headerVM.setTitle(channelInfo.name)
    }

    val input = remember { mutableStateOf("") }

    val listState = rememberLazyListState()


    LaunchedEffect(items.size) {
        if (listState.firstVisibleItemIndex < 2) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }

    LaunchedEffect(listState, items.size) {
        snapshotFlow {
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            lastVisibleIndex >= total - 3
        }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                messagesViewModel.loadMore()
            }
    }

    BackHandler { navController.popBackStack() }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextInput(
                    input,
                    context.getString(R.string.empty_message),
                    online,
                    modifier = Modifier.weight(1f),
                    keyboardType = KeyboardType.Text)
                Spacer(Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        messagesViewModel.sendText(input.value)
                        input.value = ""
                    },
                    enabled = input.value.isNotBlank() && online
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                items.isEmpty() -> {
                    Text(
                        text = context.getString(R.string.empty_messages),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState,
                        reverseLayout = true,
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(items) { _, msg ->
                            MessageRow(
                                message = Message(
                                    msg.serverId,
                                    msg.from,
                                    msg.to,
                                    msg.data,
                                    msg.time
                                ),
                                isMine = msg.from == headerUI.userName,
                                onImageClick = { link ->
                                    if (msg.data.image != null) {
                                        navController.navigate(Screen.Image.routeFor(msg.data.image))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}