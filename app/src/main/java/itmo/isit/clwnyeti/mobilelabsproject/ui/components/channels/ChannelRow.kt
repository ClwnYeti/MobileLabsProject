package itmo.isit.clwnyeti.mobilelabsproject.ui.components.channels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.ChannelInfo

@Composable
fun ChannelRow(
    item: ChannelInfo,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}