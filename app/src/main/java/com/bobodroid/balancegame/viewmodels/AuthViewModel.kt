package com.bobodroid.balancegame.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.MainActivity
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.data.AuthRepository
import com.bobodroid.balancegame.util.Resource
import com.bobodroid.balancegame.viewmodels.dataViewModels.SaveId
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserData
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserIdRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userIdRepository: UserIdRepository
) : ViewModel() {

    val localId = MutableStateFlow("")

    init {



        viewModelScope.launch(Dispatchers.IO) {
            userIdRepository.getAllUserId().distinctUntilChanged()
                .collect(){userId ->
                    if(userId.isNullOrEmpty()) {
                        localIdAdd()
                        Log.d(TAG, "로컬 유저아이디 없음") }
                    else {
                        val loadId = userId.last()
                        localId.emit(loadId.toString())


                    }
                }
        }



    }

    fun localIdAdd() {
        viewModelScope.launch {
            if(localId.value == "")
            {
                userIdRepository.addUserId(userId = SaveId(id = UUID.randomUUID().toString()))
            }
        }
    }



    var isLoggedIn = MutableStateFlow<Boolean>(false)

    var registerIsLoadingFlow = MutableStateFlow<Boolean>(false)

    val registerSuccessFlow = MutableStateFlow(false)

    var registerCompleteFlow = MutableSharedFlow<Unit>()

    var registerEmailInputFlow = MutableStateFlow<String>("")

    var registerNicknameFlow = MutableStateFlow<String>("")

    var registerPasswordInputFlow = MutableStateFlow<String>("")

    var registerPasswordConfirmInputFlow = MutableStateFlow<String>("")

    val successLogin = MutableStateFlow(false)

    var logInEmailInputFlow = MutableStateFlow("")

    var logInPasswordInputFlow = MutableStateFlow("")

    var logInIsLoadingFlow = MutableStateFlow(false)

    val needAuthContext = MutableStateFlow(false)









    fun loginUser() = viewModelScope.launch {
        repository.loginUser(logInEmailInputFlow.value, logInPasswordInputFlow.value).collect{ result ->
            when(result) {
                is Resource.Success -> {
                    successLogin.emit(true)
                    delay(1500)
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
                    Log.d(TAG, "가입성공")
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

    fun userDataUpdate() {
        viewModelScope.launch {
            successLogin.collectLatest {
                delay(1000)
                if(successLogin.value == true) {
                    val user = Firebase.auth.currentUser

                    val firebaseCurrentEmail = user?.let { it.email }

                    currentUserFlow.emit(firebaseCurrentEmail.toString())

                    Log.d(TAG, "커널트 유저 ${currentUserFlow.value}")
                }
            }
        }
    }

    val currentUserFlow = MutableStateFlow("")

    val db = Firebase.firestore

    val userEmail = MutableStateFlow("")

    val userNickname = MutableStateFlow("")


    fun loadUserData() {
        db.collection("userdatas")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    currentUserFlow.collectLatest {
                        if (successLogin.value == true) {

                            val userDatas = result.toObjects(UserData::class.java)

                            val matchUserEmail = userDatas.filter { it.email == currentUserFlow.value }.lastOrNull()

                            delay(1000)
                            val matchUserNickname = matchUserEmail?.nickname

                            logInIsLoadingFlow.emit(false)

                            isLoggedIn.emit(true)

                            userNickname.emit(matchUserNickname!!)
                            Log.d(TAG, "매칭 닉네임  ${matchUserEmail.nickname}")
                            Log.d(TAG, "매칭 닉네임  ${matchUserNickname}")

                            Log.d(TAG, "매칭 유저데이터 ${matchUserEmail}")
                        }
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting UserData documents.", exception)
            }
    }


    fun registerNickName() {
        val newUserData = UserData(registerEmailInputFlow.value, registerNicknameFlow.value)
        db.collection("userdatas")
            .add(newUserData.asHasMap())
            .addOnSuccessListener { documentReference ->
                val addEmail = userEmail
                val addUserNickname = userNickname
                viewModelScope.launch {
                    registerSuccessFlow.collectLatest {
                        if (registerSuccessFlow.value == true) {
                            registerEmailInputFlow.emit("")
                            registerPasswordInputFlow.emit("")
                            registerPasswordConfirmInputFlow.emit("")
                            registerNicknameFlow.emit("")
                            registerCompleteFlow.emit(Unit)
                            registerIsLoadingFlow.emit(false)
                            registerSuccessFlow.emit(false)
                            addEmail.emit(newUserData.email)
                            addUserNickname.emit(newUserData.nickname)
                        }
                    }
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "닉네임 생성 실패", e)
            }
    }







}
