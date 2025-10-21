package itmo.isit.clwnyeti.mobilelabsproject.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.Contact
import itmo.isit.clwnyeti.mobilelabsproject.logic.ContactsWrapper
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts.ContactCard
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.MobileLabsProjectTheme

class ContactActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras: Bundle? = intent.extras
        val contact = extras?.getSerializable(getString(R.string.contact_info), Contact::class.java)
        val contacts = extras?.getSerializable(getString(R.string.contacts_info), ContactsWrapper::class.java)
        val key = getString(R.string.contacts_info)
        enableEdgeToEdge()
        setContent {
            BackHandler {
                moveToMainActivity(this, key, contacts)
            }
            MobileLabsProjectTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    if (contact != null) {
                        ContactCard(contact, modifier = Modifier.padding(innerPadding))
                    } else {
                        moveToMainActivity(this, key, contacts)
                    }
                }
            }
        }
    }
}

fun moveToMainActivity(context: Context, key: String, contacts: ContactsWrapper?) {
    val intent = Intent(context, MainActivity::class.java)
    if (contacts != null) {
        intent.putExtra(key, contacts)
    }
    context.startActivity(intent)
}