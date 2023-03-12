package com.bobodroid.balancegame.di

import android.content.Context
import androidx.room.Room
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserDatabase
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
private object DatabaseModule {




    @Provides
    fun provideSaveIdDao(userDatabase: UserDatabase) : UserDatabaseDao{
        return userDatabase.UserIdDao()
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "User_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}