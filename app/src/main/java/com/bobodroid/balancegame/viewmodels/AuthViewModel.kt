package com.bobodroid.balancegame.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.data.AuthRepository
import com.bobodroid.balancegame.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var isLoggedIn = MutableStateFlow<Boolean>(false)

    var isLoadingFlow = MutableStateFlow<Boolean>(false)

    var registerCompleteFlow = MutableSharedFlow<Unit>()

    var emailInputFlow = MutableStateFlow<String>("")

    var passwordInputFlow = MutableStateFlow<String>("")

    var passwordConfirmInputFlow = MutableStateFlow<String>("")


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect{ result ->
            when(result) {
                is Resource.Success -> {
                    isLoggedIn.value = true

                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }
    }

    fun registerUser() = viewModelScope.launch {
        repository.registerUser(emailInputFlow.value, passwordInputFlow.value).collect{ result ->
            when(result) {
                is Resource.Success -> {
                    registerCompleteFlow.emit(Unit)
                    isLoadingFlow.emit(false)
                    emailInputFlow.emit("")
                    passwordInputFlow.emit("")
                    passwordConfirmInputFlow.emit("")
                }
                is Resource.Loading -> {
                    isLoadingFlow.emit(true)
                }
                is Resource.Error -> {
                    isLoadingFlow.emit(true)
                }
            }
        }
    }


}
