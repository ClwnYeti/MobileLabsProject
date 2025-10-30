package itmo.isit.clwnyeti.mobilelabsproject.logic.repositories

import android.content.Context
import androidx.core.content.edit
import itmo.isit.clwnyeti.mobilelabsproject.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TokenPreferencesRepository(
    context: Context,
): TokenInnerRepository {
    private val prefs = context.getSharedPreferences(
        context.getString(R.string.auth_preferences),
        Context.MODE_PRIVATE
    )
    private val tokenKey = context.getString(R.string.token)
    private val userNameKey = context.getString(R.string.username)

    override fun save(userName: String, token: String) {
        prefs.edit(commit = true) {
            putString(userNameKey, userName)
            putString(tokenKey, token)
        }
        _user.value = userName
        _token.value = token
    }

    override fun getToken(): String? = prefs.getString(tokenKey, null)
    override fun getUserName(): String? = prefs.getString(userNameKey, null)
    private val _token = MutableStateFlow(prefs.getString("token", null))
    private val _user = MutableStateFlow(prefs.getString("username", null))

    override val userNameFlow: Flow<String?> = _user
    override val tokenFlow: Flow<String?> = _token

    override fun clear() {
        prefs.edit(commit = true) {
            clear()
        }
        _user.value = null
        _token.value = null
    }

    override fun isAuthorized(): Boolean = !getToken().isNullOrBlank()
}