package com.adyen.android.assignment.domain

import android.content.Context
import androidx.room.*
import com.adyen.android.assignment.data.local.AstronomyPictureDao
import com.adyen.android.assignment.data.network.AstronomyPicture

const val DB_VERSION = 1
const val DB_NAME ="database"

@Database(
    entities = [AstronomyPicture::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun astronomyPictureDao(): AstronomyPictureDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java, DB_NAME
            ).build()
    }
}