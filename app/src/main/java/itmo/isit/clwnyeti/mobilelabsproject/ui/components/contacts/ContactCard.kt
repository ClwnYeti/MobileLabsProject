package itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.Contact
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.Black
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.Purple80
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey40
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey80

@Composable
fun ContactCard(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp, 5.dp)
            .background(PurpleGrey40),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (contact.photo != null) {
            Image(
                bitmap = contact.photo.asImageBitmap(),
                contentDescription = stringResource(R.string.contact_photo))
        } else {
            Box(modifier = Modifier.padding(vertical = 10.dp)) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(PurpleGrey80)
                ) {
                    Text(
                        contact.name.first().uppercase(),
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        color = Black
                    )
                }
            }

        }
        Text(contact.name)
        Text(contact.phone)
        Text(contact.email)
    }
}