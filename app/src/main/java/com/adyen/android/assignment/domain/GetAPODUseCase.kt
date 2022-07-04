package com.adyen.android.assignment.domain

import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.network.PlanetaryDataUIState
import com.adyen.android.assignment.network.ResponseResult
import com.adyen.android.assignment.utils.FlowUseCase
import com.adyen.android.assignment.utils.StringResourceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetAPODUseCase : FlowUseCase<Unit, PlanetaryDataUIState>

class DefaultGetAPODUseCase @Inject constructor(
    private val repository: PlanetaryRepository,
    private val stringResourceManager: StringResourceManager
) : GetAPODUseCase{

    override fun invoke(request: Unit): Flow<PlanetaryDataUIState> = flow {
        emit(PlanetaryDataUIState.Loading)
        emit(
            when (val response = repository.getPictures()) {
                is ResponseResult.Success -> onSuccess(response)
                is ResponseResult.Failure -> onFailure(response)
                is ResponseResult.NetworkError -> onNetworkError()
            }
        )
    }

    private fun onSuccess(result: ResponseResult.Success<List<AstronomyPicture>>) =
        PlanetaryDataUIState.Success((result.data))

    private fun onNetworkError(): PlanetaryDataUIState.Failure {
        return PlanetaryDataUIState.Failure(stringResourceManager.getString(R.string.unexpected_error))
    }

    private fun onFailure(
        result: ResponseResult.Failure
    ): PlanetaryDataUIState.Failure {
        return PlanetaryDataUIState.Failure(
            result.error.message ?: stringResourceManager.getString(R.string.unexpected_error)
        )
    }
}