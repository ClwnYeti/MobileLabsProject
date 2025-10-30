package itmo.isit.clwnyeti.mobilelabsproject.logic.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import itmo.isit.clwnyeti.mobilelabsproject.logic.dto.Error
import itmo.isit.clwnyeti.mobilelabsproject.logic.models.data.LoginFormInput
import itmo.isit.clwnyeti.mobilelabsproject.logic.repositories.TokenRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): ViewModel() {
    val error: MutableState<Error?> = mutableStateOf(null)
    private val isLoggedIn: MutableState<Boolean> = mutableStateOf(false)


    val loginFormInput: LoginFormInput = LoginFormInput(
        mutableStateOf(""),
        mutableStateOf(""),
    )

    suspend fun loginClick(): Boolean {
        return try {
            error.value = null
            isLoggedIn.value = false

            tokenRepository.login(loginFormInput.username.value, loginFormInput.password.value)
            true
        } catch (e: HttpException) {
            error.value = Error(e.code().toString(), e.message())
            false
        } catch (e: IOException) {
            error.value = Error("500", e.message ?: "Internal Error")
            false
        }
    }
}
