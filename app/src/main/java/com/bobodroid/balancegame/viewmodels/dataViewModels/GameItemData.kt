package com.bobodroid.balancegame.viewmodels.dataViewModels

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

enum class ItemKind(val kindName: String) {
    ALL("모두"),
    FOOD("음식"),
    LOVE("연애"),
    TRAVEL("여행"),
    ETC("기타"),
    NULL("NULL")
}

data class GameItem (
    var id: String = UUID.randomUUID().toString(),
    var makerName: String = "",
    var firstItem: String = "",
    var secondItem: String = "",
    var private: Boolean = false,
    var selectItem: Int = 0,
    var itemKind: ItemKind = ItemKind.FOOD) {

    constructor(data: QueryDocumentSnapshot) : this() {
        this.id = data.id
        this.makerName = data["makerName"] as String? ?: ""
        this.firstItem = data["firstItem"] as String? ?: ""
        this.secondItem = data["secondItem"] as String? ?: ""
        this.private = data["private"] as Boolean? ?: false
        this.selectItem = data["selectItem"] as Int? ?: 0
        this.itemKind = data["itemKind"] as ItemKind? ?: ItemKind.FOOD
    }

    fun asHasMap() : HashMap<String, Any>{
        return hashMapOf(
            "id" to this.id,
            "makerName" to this.makerName,
            "firstItem" to this.firstItem,
            "secondItem" to this.secondItem,
            "private" to this.private,
            "selectItem" to this.selectItem,
            "itemKind" to this.itemKind
        )
    }

}