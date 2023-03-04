package com.bobodroid.balancegame.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MyPageViewModel: ViewModel() {

    val myPageChangeListButton = MutableStateFlow(1)
}