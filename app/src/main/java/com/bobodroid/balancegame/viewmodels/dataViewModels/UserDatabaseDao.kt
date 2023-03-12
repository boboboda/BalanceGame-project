package com.bobodroid.balancegame.viewmodels.dataViewModels

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDatabaseDao {

    @Query("SELECT * from UserId_table")
    fun getUserId(): Flow<List<SaveId>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(saveId: SaveId)

    @Delete
    suspend fun deleteUserId(saveId: SaveId)

    @Query("DELETE from UserId_table")
    suspend fun deleteAll()
}