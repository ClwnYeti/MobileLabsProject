package itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsViewModel
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey40

@Composable
fun ContactsSection(
    contactVM: ContactsViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PurpleGrey40),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(contactVM.items) {
            item -> Contact(item)
        }
    }
}