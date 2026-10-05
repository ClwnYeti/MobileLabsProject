package itmo.isit.clwnyeti.mobilelabsproject.ui.components.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.ui.theme.PurpleGrey40

@Composable
fun EmptyContactSection(
    userDeclinedPermissionRequest: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    askPermissionLogic: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PurpleGrey40),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!userDeclinedPermissionRequest.value) {
            Button(askPermissionLogic) {
                Text(stringResource(R.string.ask_permission_button_text))
            }
        } else {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.cannot_work_without_permission))
        }
    }

}