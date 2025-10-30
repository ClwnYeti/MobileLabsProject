package itmo.isit.clwnyeti.mobilelabsproject.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import itmo.isit.clwnyeti.mobilelabsproject.R
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Error
import itmo.isit.clwnyeti.mobilelabsproject.ui.components.common.TextInput

@Composable
fun AuthForm(
    modifier: Modifier,
    userNameValue: MutableState<String>,
    passwordValue: MutableState<String>,
    errorValue: MutableState<Error?>,
    loginLogic: () -> Unit,) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            inputValue = userNameValue,
            placeholder = stringResource(R.string.username_form),
            enabled = true,
            keyboardType = KeyboardType.Text
        )
        TextInput(
            inputValue = passwordValue,
            placeholder = stringResource(R.string.password_form),
            enabled = true,
            keyboardType = KeyboardType.Password
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                loginLogic()
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(text = stringResource(R.string.log_in))
        }
        if (errorValue.value != null) {
            val error = errorValue.value!!
            AssistChip(
                onClick = {},
                label = { Text(stringResource(R.string.log_in_error, error.code, error.message)) },
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}