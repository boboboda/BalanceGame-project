package com.bobodroid.balancegame.viewmodels.dataViewModels

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        SaveId::class
    ], version = 1, exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {

    abstract  fun UserIdDao() : UserDatabaseDao
}