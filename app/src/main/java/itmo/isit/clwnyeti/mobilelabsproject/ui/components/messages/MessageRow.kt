package itmo.isit.clwnyeti.mobilelabsproject.ui.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Message
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.MessageImageData

@Composable
fun MessageRow(
    message: Message,
    isMine: Boolean,
    onImageClick: (MessageImageData) -> Unit
) {
    val bg = if (isMine) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.surfaceVariant

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 320.dp)
                    .background(bg, shape = MaterialTheme.shapes.medium)
                    .padding(10.dp)
            ) {
                Text(
                    text = message.sender,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )


                if (message.data.text != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = message.data.text.value,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else if (message.data.image != null) {
                    Spacer(Modifier.height(8.dp))
                    AsyncImage(
                        model = message.data.image.toThumbImage(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp, max = 220.dp)
                            .clickable { onImageClick(message.data.image) }
                    )
                }
            }
        }
    }
}