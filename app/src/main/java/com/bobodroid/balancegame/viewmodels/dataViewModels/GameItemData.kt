package com.bobodroid.balancegame.viewmodels.dataViewModels

enum class ItemKind(val kindName: String) {
    ALL("모두"),
    FOOD("음식"),
    LOVE("연애"),
    TRAVEL("여행"),
    ETC("기타"),
    NULL("NULL")
}

data class GameItem (val id: Int? = null, val makerName:String = "", val firstItem: String = "", val secondItem: String = "", val itemKind: ItemKind)