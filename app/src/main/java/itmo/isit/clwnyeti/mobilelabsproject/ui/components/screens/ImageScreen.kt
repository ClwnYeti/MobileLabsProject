package itmo.isit.clwnyeti.mobilelabsproject.ui.components.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageImageData
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.HeaderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(
    navController: NavController,
    headerVM: HeaderViewModel,
    imageData: MessageImageData?
) {
    if (imageData == null) {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        headerVM.setTitle(null)
    }
    val imageUrl = remember(imageData) {
        imageData?.toFullImage()
    }
    val context = LocalContext.current

    BackHandler { navController.popBackStack() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = context.getString(R.string.close),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}