package com.adyen.android.assignment.domain

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.data.network.AstronomyPicture
import retrofit2.http.GET


interface PlanetaryService {
    /**
     * APOD - Astronomy Picture of the day.
     * See [the docs](https://api.nasa.gov/) and [github micro service](https://github.com/nasa/apod-api#docs-)
     */
    @GET("/planetary/apod?count=20&api_key=${BuildConfig.API_KEY}")
    suspend fun getPictures(): List<AstronomyPicture>
}
