package com.adyen.android.assignment.di

import android.content.Context
import com.adyen.android.assignment.data.local.AstronomyPictureDao
import com.adyen.android.assignment.domain.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDB {
        return RoomDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideProductDao(roomDB: RoomDB): AstronomyPictureDao {
        return roomDB.astronomyPictureDao()
    }
}