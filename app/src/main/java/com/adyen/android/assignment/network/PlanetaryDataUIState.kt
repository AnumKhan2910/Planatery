package com.adyen.android.assignment.network

import com.adyen.android.assignment.data.network.AstronomyPicture

sealed class PlanetaryDataUIState{
    data class Success(val data: List<AstronomyPicture>): PlanetaryDataUIState()
    data class Failure(val message: String): PlanetaryDataUIState()
    object Loading: PlanetaryDataUIState()
}