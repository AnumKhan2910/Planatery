package com.adyen.android.assignment.domain

import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.network.*
import com.adyen.android.assignment.utils.StringResourceManager
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAPODUseCaseTest {

    @Mock
    private lateinit var stringResourceManager: StringResourceManager

    @Mock
    private lateinit var repository: PlanetaryRepository


    private lateinit var getAPODUseCase: GetAPODUseCase

    @Before
    fun setup() {
        getAPODUseCase = DefaultGetAPODUseCase(
            repository,
            stringResourceManager
        )
    }

    @Test
    fun `execute get pictures and expect network failure`() = runBlocking {
        val responseResult = ResponseResult.NetworkError
        val message = "not able to reach network"
        val errorViewState = PlanetaryDataUIState.Failure(message)
        val expectedViewStates = arrayListOf<PlanetaryDataUIState>().apply {
            add(PlanetaryDataUIState.Loading)
            add(errorViewState)
        }.toTypedArray()

        whenever(repository.getPictures()).thenReturn(responseResult)
        whenever(stringResourceManager.getString(R.string.unexpected_error)).thenReturn(message)

        val actualViewStates = getAPODUseCase(Unit).take(2).toList().toTypedArray()

        Assert.assertNotNull(actualViewStates)
        Assert.assertArrayEquals(expectedViewStates, actualViewStates)
    }

    @Test
    fun `execute get pictures and expect api failure`() = runBlocking {
        val message = "validation failed"
        val errorResponse = ErrorResponse(
            statusCode = 402,
            message = message
        )
        val responseResult = ResponseResult.Failure(errorResponse)
        val errorViewState = PlanetaryDataUIState.Failure(message)
        val expectedViewStates = arrayListOf<PlanetaryDataUIState>().apply {
            add(PlanetaryDataUIState.Loading)
            add(errorViewState)
        }.toTypedArray()

        whenever(repository.getPictures()).thenReturn(responseResult)

        val actualViewStates = getAPODUseCase(Unit).take(2).toList().toTypedArray()

        Assert.assertNotNull(actualViewStates)
        Assert.assertArrayEquals(expectedViewStates, actualViewStates)
    }

    @Test
    fun `execute get pictures and expect success response`() = runBlocking {
        val data = mutableListOf<AstronomyPicture>()
        val responseResult = ResponseResult.Success(data)
        val successUIData = PlanetaryDataUIState.Success(data)
        val expectedViewStates = arrayListOf<PlanetaryDataUIState>().apply {
            add(PlanetaryDataUIState.Loading)
            add(successUIData)
        }.toTypedArray()

        whenever(repository.getPictures()).thenReturn(responseResult)

        val actualViewStates = getAPODUseCase(Unit).take(2).toList().toTypedArray()

        Assert.assertNotNull(actualViewStates)
        Assert.assertArrayEquals(expectedViewStates, actualViewStates)
    }
}