package com.adyen.android.assignment

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.adyen.android.assignment.data.local.AstronomyPictureDao
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.domain.RoomDB
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AstronomyPictureDaoTest {

    private lateinit var roomDB: RoomDB
    private lateinit var astronomyPictureDao: AstronomyPictureDao

    @Before
    fun setup(){
        roomDB = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),RoomDB::class.java
        ).allowMainThreadQueries().build()
        astronomyPictureDao = roomDB.astronomyPictureDao()
    }

    @After
    fun tearDown(){
        roomDB.close()
    }

    @Test
    fun insertPicture() = runBlocking {
        val astronomyPicture = AstronomyPicture("Dummy", "My Test Data", "2020-01-01")

        astronomyPictureDao.insert(astronomyPicture)

        val data : List<AstronomyPicture> =  astronomyPictureDao.getAllData().first()

        Assert.assertTrue(data[0].date == astronomyPicture.date)
    }


    @Test
    fun deletePicture() = runBlocking {
        val astronomyPicture = AstronomyPicture("Dummy", "My Test Data", "2020-01-01")

        astronomyPictureDao.insert(astronomyPicture)
        astronomyPictureDao.deleteData(astronomyPicture)

        val data : List<AstronomyPicture> =  astronomyPictureDao.getAllData().first()

        Assert.assertTrue(data.isEmpty())
    }

}