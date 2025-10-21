package itmo.isit.clwnyeti.mobilelabsproject.activities

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsWrapper
import itmo.isit.clwnyeti.mobilelabsproject.logic.tryToRefreshContacts
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts.ContactsSection
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts.EmptyContactSection
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.MobileLabsProjectTheme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {
    private val contractsVM: ContactsViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            contractsVM.canRead.value = true
            contractsVM.refreshContacts(this)
        } else {
            contractsVM.userDeclinedPermissionRequest.value = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras: Bundle? = intent.extras
        val contacts = extras?.getSerializable(getString(R.string.contacts_info), ContactsWrapper::class.java)
        if (contacts != null) {
            contractsVM.items.clear()
            contractsVM.items.addAll(contacts.items)
        }
        super.onCreate(savedInstanceState)
        tryToRefreshContacts(contractsVM)
        enableEdgeToEdge()
        setContent {
            MobileLabsProjectTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    Row(modifier = Modifier.Companion.padding(innerPadding)) {
                        if (contractsVM.canRead.value) {
                            ContactsSection(contractsVM)
                        } else {
                            EmptyContactSection(
                                contractsVM.userDeclinedPermissionRequest,
                                modifier = Modifier.Companion.fillMaxSize()
                            ) {
                                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                                tryToRefreshContacts(contractsVM)
                            }
                        }
                    }
                }
            }
        }
    }
}