package itmo.isit.clwnyeti.mobilelabsproject.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    inputValue: MutableState<String>,
    placeholder: String,
    enabled: Boolean,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = inputValue.value,
        onValueChange = { newValue ->
            inputValue.value = newValue
        },
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(60.dp),
        enabled = enabled,
        readOnly = false,
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
