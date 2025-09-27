package itmo.isit.clwnyeti.mobilelabsproject.logic

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.MutableState
import androidx.core.database.getStringOrNull

fun Context.checkContactReadPermission(canReadContacts: MutableState<Boolean>) {
    canReadContacts.value = checkSelfPermission(Manifest.permission.READ_CONTACTS) ==
            PackageManager.PERMISSION_GRANTED;
}

fun Context.tryToRefreshContacts(contractsVM: ContactsViewModel) {
    checkContactReadPermission(contractsVM.canRead)
    if (contractsVM.canRead.value && contractsVM.items.isEmpty()) {
        contractsVM.refreshContacts(this)
    }
}

fun Context.fetchAllContacts(): List<Contact> {
    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        .use { cursor: Cursor? ->
            if (cursor == null) return emptyList()
            return buildList {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNumber =
                        cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    add(Contact(name, phoneNumber))
                }
            }
        }
}