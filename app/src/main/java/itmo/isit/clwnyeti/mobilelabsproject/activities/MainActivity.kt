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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsWrapper
import itmo.isit.clwnyeti.mobilelabsproject.logic.tryToRefreshContacts
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts.ContactsSection
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.MobileLabsProjectTheme
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey40


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
                        }
                        else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(PurpleGrey40),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (!contractsVM.userDeclinedPermissionRequest.value) {
                                    permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                                    tryToRefreshContacts(contractsVM)
                                }
                                else {
                                    Text(
                                        text = stringResource(R.string.cannot_work_without_permission),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}