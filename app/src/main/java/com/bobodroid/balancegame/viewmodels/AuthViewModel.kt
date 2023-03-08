package com.bobodroid.balancegame.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.MainActivity
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.data.AuthRepository
import com.bobodroid.balancegame.util.Resource
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    var registerIsLoadingFlow = MutableStateFlow<Boolean>(false)

    val registerSuccessFlow = MutableStateFlow(false)

    var registerCompleteFlow = MutableSharedFlow<Unit>()

    var registerEmailInputFlow = MutableStateFlow<String>("")

    var registerNicknameFlow = MutableStateFlow<String>("")

    var registerPasswordInputFlow = MutableStateFlow<String>("")

    var registerPasswordConfirmInputFlow = MutableStateFlow<String>("")

    var logInEmailInputFlow = MutableStateFlow("")

    var logInPasswordInputFlow = MutableStateFlow("")

    var logInIsLoadingFlow = MutableStateFlow(false)




    fun loginUser() = viewModelScope.launch {
        repository.loginUser(logInEmailInputFlow.value, logInPasswordInputFlow.value).collect{ result ->
            when(result) {
                is Resource.Success -> {
//                    registerSuccessFlow.emit(true)
                    delay(1500)
                    logInIsLoadingFlow.emit(false)
                    isLoggedIn.emit(true)
                    logInEmailInputFlow.emit("")
                    logInPasswordInputFlow.emit("")
                }
                is Resource.Loading -> {
                    logInIsLoadingFlow.emit(true)
                }
                is Resource.Error -> {
                    logInIsLoadingFlow.emit(true)
                }
            }
        }
    }

    fun registerUser() = viewModelScope.launch {
        repository.registerUser(registerEmailInputFlow.value, registerPasswordInputFlow.value).collect{ result ->
            when(result) {
                is Resource.Success -> {
                    registerSuccessFlow.emit(true)
                    registerCompleteFlow.emit(Unit)
                    registerIsLoadingFlow.emit(false)
                    registerEmailInputFlow.emit("")
                    registerPasswordInputFlow.emit("")
                    registerPasswordConfirmInputFlow.emit("")
                }
                is Resource.Loading -> {
                    registerIsLoadingFlow.emit(true)
                }
                is Resource.Error -> {
                    registerIsLoadingFlow.emit(true)
                }
            }
        }
    }


}
