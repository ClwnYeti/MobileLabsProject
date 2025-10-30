package itmo.isit.clwnyeti.mobilelabsproject.logic.models.data

import androidx.compose.runtime.MutableState

data class LoginFormInput(
    val username: MutableState<String>,
    val password: MutableState<String>,
)
