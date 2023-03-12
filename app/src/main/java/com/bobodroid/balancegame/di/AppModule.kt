package com.bobodroid.balancegame.di

import com.bobodroid.balancegame.data.AuthRepository
import com.bobodroid.balancegame.data.AuthRepositoryImpl
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserDatabaseDao
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserIdRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideUserDataRepository(
        userIdDao: UserDatabaseDao) : UserIdRepository {
        return UserIdRepository(
            userIdDao
        )
    }



}