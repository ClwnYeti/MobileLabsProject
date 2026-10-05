package itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsViewModel
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey40
import androidx.core.net.toUri

@Composable
fun ContactsSection(
    contactVM: ContactsViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    if (contactVM.items.count() == 0) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PurpleGrey40),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.no_contacts)
            )
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(PurpleGrey40),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Text(stringResource(id = R.string.contacts_count, contactVM.items.count())) }

                items(contactVM.items) { item ->
                    ContactPreview(item) { item ->
                        val intent = Intent( Intent.ACTION_DIAL, "tel:${item.phone}".toUri() )
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}