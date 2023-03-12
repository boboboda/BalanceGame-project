package com.bobodroid.balancegame.viewmodels.dataViewModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.HashMap

data class UserSaveGame(
    var id: String = UUID.randomUUID().toString(),
    var GameCode: String = UUID.randomUUID().toString(),
    var saveName: String = "",
    var gameItems: List<GameItem> = listOf<GameItem>(GameItem(UUID.randomUUID().toString(), "", "", "", true ,0, ItemKind.FOOD))) {

    constructor(data: QueryDocumentSnapshot) : this() {
        this.id = data.id
        this.GameCode = data["gameCode"] as String? ?: ""
        this.saveName = data["saveName"] as String? ?: ""
        this.gameItems = data["gameItems"] as List<GameItem>? ?: listOf<GameItem>(GameItem(UUID.randomUUID().toString(), "", "", "",true ,0, ItemKind.FOOD))
    }

    fun asHasMap() : HashMap<String, Any>{
        return hashMapOf(
            "id" to this.id,
            "gameCode" to this.GameCode,
            "saveName" to this.saveName,
            "gameItems" to this.gameItems
        )
    }

}

@Entity(tableName = "UserId_table")
data class SaveId(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString()
)