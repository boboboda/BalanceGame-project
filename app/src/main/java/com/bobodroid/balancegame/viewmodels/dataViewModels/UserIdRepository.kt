package com.bobodroid.balancegame.viewmodels.dataViewModels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserIdRepository @Inject constructor(
    private val userIdDatabaseDao: UserDatabaseDao

) {

    suspend fun addUserId(userId: SaveId) = userIdDatabaseDao.insert(userId)

    suspend fun deleteUserId(userId: SaveId) = userIdDatabaseDao.deleteUserId(userId)

    suspend fun deleteAllUserId(userId: SaveId) = userIdDatabaseDao.deleteAll()

    fun getAllUserId(): Flow<List<SaveId>> = userIdDatabaseDao.getUserId().flowOn(Dispatchers.IO).conflate()

}