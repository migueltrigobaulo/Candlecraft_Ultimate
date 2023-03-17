package hc.candlecraft.candlecraftultimate.feature_login.presentation.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case.RecipeUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnabled = MutableLiveData<Boolean>()
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _animationTimer1Finished = MutableLiveData<Boolean>()
    val animationTimer1Finished: LiveData<Boolean> = _animationTimer1Finished

    private val _animationTimer2Finished = MutableLiveData<Boolean>()
    val animationTimer2Finished: LiveData<Boolean> = _animationTimer2Finished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _finishedLoading = MutableSharedFlow<Boolean>()
    val finishedLoading = _finishedLoading.asSharedFlow()

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length > 6
    }

    fun animationTimer1Finished(){
        _animationTimer1Finished.value = true
    }

    fun animationTimer2Finished(){
        _animationTimer2Finished.value = true
    }

    suspend fun onLoginClicked() {
        _isLoading.value = true
        delay(2000L)
        loggedIn()
    }

    suspend fun loggedIn(){
        _finishedLoading.emit(true)
    }
}