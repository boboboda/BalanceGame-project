package com.bobodroid.balancegame.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {

    var isLoggedIn = MutableStateFlow<Boolean>(false)



}


data class User(val email: String, val password: String, val username: String)