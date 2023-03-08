package com.bobodroid.balancegame.viewmodels.dataViewModels

import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.HashMap

data class UserData(
    var email: String = "",
    var nickname: String = "") {

    constructor(data: QueryDocumentSnapshot) : this() {
        this.email = data.id
        this.nickname = data["nickname"] as String? ?: ""
    }

    fun asHasMap() : HashMap<String, Any>{
        return hashMapOf(
            "email" to this.email,
            "makerName" to this.nickname
        )
    }

}