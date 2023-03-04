package com.bobodroid.balancegame.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel: ViewModel() {

    val bottomSelectedState = MutableStateFlow(1)

    val selectedCardId = MutableStateFlow(1)
}