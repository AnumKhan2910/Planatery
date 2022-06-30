package com.adyen.android.assignment.domain

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.network.ResponseResult
import com.adyen.android.assignment.network.callApi
import javax.inject.Inject

interface PlanetaryRepository {
    suspend fun getPictures() : ResponseResult<List<AstronomyPicture>>
}

class DefaultPlanetaryRepository @Inject constructor(
    private val planetaryService: PlanetaryService
) : PlanetaryRepository {

    override suspend fun getPictures(): ResponseResult<List<AstronomyPicture>> =
        callApi { planetaryService.getPictures() }
}