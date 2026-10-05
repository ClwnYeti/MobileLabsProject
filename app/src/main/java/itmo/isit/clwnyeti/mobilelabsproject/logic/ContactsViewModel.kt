package itmo.isit.clwnyeti.mobilelabsproject.logic

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ContactsViewModel: ViewModel() {
    val items = mutableStateListOf<Contact>()
    val canRead = mutableStateOf(false)
    val userDeclinedPermissionRequest = mutableStateOf(false)
    fun refreshContacts(context: Context) {
        items.clear()
        items.addAll(context.fetchAllContacts())
    }
}