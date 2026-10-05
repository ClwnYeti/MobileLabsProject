package itmo.isit.clwnyeti.mobilelabsproject.logic

import java.io.Serializable

data class Contact(
    val name: String?,
    val phone: String?,
    val email: String?,
) : Serializable
