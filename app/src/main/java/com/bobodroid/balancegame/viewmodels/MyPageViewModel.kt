package com.bobodroid.balancegame.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.MainActivity
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyPageViewModel: ViewModel() {

    init {

    }

    val myPageChangeListButton = MutableStateFlow(1)


    val db = Firebase.firestore









}