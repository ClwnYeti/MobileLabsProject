package itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.activities.ContactActivity
import itmo.isit.clwnyeti.mobilelabsproject.logic.Contact

@Composable
fun ContactPreview(
    contact: Contact,
    modifier: Modifier = Modifier,
    moveToContactActivity: (Contact) -> Unit
) {
    Button(
        onClick = {
            moveToContactActivity(contact)
        },
        modifier =
            Modifier
                .fillMaxSize()
                .padding(5.dp, 5.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(contact.name)
            Text(contact.phone)
        }

    }
}