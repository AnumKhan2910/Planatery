package com.adyen.android.assignment.ui

import androidx.lifecycle.*
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.domain.GetAPODUseCase
import com.adyen.android.assignment.network.PlanetaryDataUIState
import com.adyen.android.assignment.utils.ToastManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class APODListViewModel @Inject constructor(
    private val getAPODUseCase: GetAPODUseCase,
    private val toastManager: ToastManager
): ViewModel() {

    private val _apodList = MutableLiveData<List<AstronomyPicture>>()
    val apodList: LiveData<List<AstronomyPicture>>
        get() = _apodList

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAPODUseCase(Unit).collectLatest {
                if (it is PlanetaryDataUIState.Success) {
                    _apodList.value = it.data as List<AstronomyPicture>
                } else if (it is PlanetaryDataUIState.Failure) {
                    toastManager.show(it.message)
                }
            }
        }
    }
}