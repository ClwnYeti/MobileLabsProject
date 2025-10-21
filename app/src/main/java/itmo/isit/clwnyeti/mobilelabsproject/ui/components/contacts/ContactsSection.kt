package itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.activities.ContactActivity
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsWrapper
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey40

@Composable
fun ContactsSection(
    contactVM: ContactsViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val keyValueContact = stringResource(R.string.contact_info)
    val keyValueContacts = stringResource(R.string.contacts_info)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PurpleGrey40),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(contactVM.items) { item ->
            ContactPreview(item) { item ->
                val intent = Intent(context, ContactActivity::class.java)
                intent.putExtra(keyValueContact, item)
                intent.putExtra(keyValueContacts, ContactsWrapper(contactVM.items.toTypedArray()))
                context.startActivity(intent)
            }
        }
    }
}