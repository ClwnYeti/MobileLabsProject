package itmo.isit.clwnyeti.mobilelabsproject.logic

import android.graphics.Bitmap
import java.io.Serializable

data class Contact(
    val name: String,
    val phone: String,
    val email: String,
    val photo: Bitmap?
) : Serializable
