package com.adyen.android.assignment.data.local

import androidx.room.*
import com.adyen.android.assignment.data.network.AstronomyPicture
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyPictureDao {

    @Insert
    suspend fun insert(data: AstronomyPicture)

    @Query("SELECT * FROM astronomyPictures")
    fun getAllData() : Flow<List<AstronomyPicture>>

    @Delete
    suspend fun deleteData(data: AstronomyPicture)
}