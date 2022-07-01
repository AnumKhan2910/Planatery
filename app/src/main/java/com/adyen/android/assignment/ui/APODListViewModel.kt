package com.adyen.android.assignment.ui

import androidx.lifecycle.*
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.domain.GetAPODUseCase
import com.adyen.android.assignment.network.PlanetaryDataUIState
import com.adyen.android.assignment.utils.ToastManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _sortByDateSelected = MutableLiveData(false)
    val sortByDateSelected: LiveData<Boolean>
        get() = _sortByDateSelected

    private val _sortByTitleSelected = MutableLiveData(false)
    val sortByTitleSelected: LiveData<Boolean>
        get() = _sortByTitleSelected

    private val _actionDismissDialog = MutableLiveData(false)
    val actionDismissDialog: LiveData<Boolean>
        get() = _actionDismissDialog

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

    fun sortByDate() {
        _sortByDateSelected.value = true
        _sortByTitleSelected.value = false
        dismissDialog()
        performDateSorting()
    }

    private fun performDateSorting() {
        _apodList.value = apodList.value?.sortedByDescending {
            it.date
        }
    }

    fun sortByTitle() {
        _sortByDateSelected.value = false
        _sortByTitleSelected.value = true
        dismissDialog()
        performTitleSorting()
    }

    private fun performTitleSorting() {
        _apodList.value = apodList.value?.sortedBy {
            it.title
        }
    }

    private fun dismissDialog() {
        viewModelScope.launch {
            delay(100)
            _actionDismissDialog.value = true
        }
    }

    fun resetDismissValue() {
        _actionDismissDialog.value = false
    }
}