package com.adyen.android.assignment.network

sealed class PlanetaryDataUIState{
    data class Success(val data: Any): PlanetaryDataUIState()
    data class Failure(val message: String): PlanetaryDataUIState()
    object Loading: PlanetaryDataUIState()
}