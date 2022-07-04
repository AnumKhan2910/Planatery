package com.adyen.android.assignment.ui

import androidx.lifecycle.*
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.local.AstronomyPictureDao
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.domain.GetAPODUseCase
import com.adyen.android.assignment.network.PlanetaryDataUIState
import com.adyen.android.assignment.utils.StringResourceManager
import com.adyen.android.assignment.utils.ToastManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class APODListViewModel @Inject constructor(
    private val getAPODUseCase: GetAPODUseCase,
    private val stringResourceManager: StringResourceManager,
    private val toastManager: ToastManager,
    private val astronomyPictureDao: AstronomyPictureDao
): ViewModel() {

    private val _apodList = MutableLiveData<List<AstronomyPicture>>()
    val apodList: LiveData<List<AstronomyPicture>>
        get() = _apodList

    private val _showErrorScreen = MutableLiveData(false)
    val showErrorScreen: LiveData<Boolean>
        get() = _showErrorScreen

    private val _showProgress = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _sortByDateSelected = MutableLiveData(false)
    val sortByDateSelected: LiveData<Boolean>
        get() = _sortByDateSelected

    private val _sortByTitleSelected = MutableLiveData(false)
    val sortByTitleSelected: LiveData<Boolean>
        get() = _sortByTitleSelected

    private val _actionDismissDialog = MutableLiveData(false)
    val actionDismissDialog: LiveData<Boolean>
        get() = _actionDismissDialog

    private var latestData: List<AstronomyPicture>? = null
    private var favouritesData: List<AstronomyPicture>? = null

    fun fetchData() {
        if (latestData == null) {
            viewModelScope.launch {
                fetchLatestData()
                fetchFavouritesData()
            }
        }
    }

    private suspend fun fetchLatestData() {
        getAPODUseCase(Unit).collectLatest {
            when(it) {
                is PlanetaryDataUIState.Loading -> {
                    _showProgress.value = true
                }
                is PlanetaryDataUIState.Failure -> {
                    _showProgress.value = false
                    _showErrorScreen.value = true
                    toastManager.show(it.message)
                }
                is PlanetaryDataUIState.Success -> {
                    _showProgress.value = false
                    latestData = it.data
                    setLatestData(it.data)
                }
            }
        }
    }


    private suspend fun fetchFavouritesData() {
        astronomyPictureDao.getAllData().collectLatest {
            favouritesData = it
            setFavouritesData(it)
        }
    }

    fun performOrdering() {
        if (sortByDateSelected.value == true) {
            performDateSorting()
        } else if (sortByTitleSelected.value == true) {
            performTitleSorting()
        }
        dismissDialog()
    }

    fun reset() {
        _sortByDateSelected.value = false
        _sortByTitleSelected.value = false
        resetList()
        dismissDialog()
    }

    fun sortByDate() {
        _sortByDateSelected.value = true
        _sortByTitleSelected.value = false
    }

    private fun resetList() {
        val list = mutableListOf<AstronomyPicture>()
        favouritesData?.let {
            list.add(getFavouritesHeader())
            list.addAll(it)
        }
        latestData?.let {
            list.add(getLatestHeader())
            list.addAll(it)
        }
        _apodList.value = list
    }

    private fun performDateSorting() {
        val list = mutableListOf<AstronomyPicture>()
        favouritesData?.let {
            list.add(getFavouritesHeader())
            list.addAll(it.sortedByDescending {
                it.date
            })
        }
        latestData?.let {
            list.add(getLatestHeader())
            list.addAll(it.sortedByDescending {
                it.date
            })
        }
        _apodList.value = list
    }

    fun sortByTitle() {
        _sortByDateSelected.value = false
        _sortByTitleSelected.value = true
    }

    private fun performTitleSorting() {
        val list = mutableListOf<AstronomyPicture>()
        favouritesData?.let {
            list.add(getFavouritesHeader())
            list.addAll(it.sortedBy {
                it.title
            })
        }
        latestData?.let {
            list.add(getLatestHeader())
            list.addAll(it.sortedBy {
                it.title
            })
        }
        _apodList.value = list
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

    fun resetErrorValue() {
        _showErrorScreen.value = false
    }

    private fun setLatestData(data: List<AstronomyPicture>) {
        val list = mutableListOf<AstronomyPicture>()
        favouritesData?.let {
            list.add(getFavouritesHeader())
            list.addAll(it)
        }
        list.add(getLatestHeader())
        list.addAll(data)
        _apodList.value = list
    }

    private fun setFavouritesData(data: List<AstronomyPicture>) {
        val list = mutableListOf<AstronomyPicture>()
        if (data.isNotEmpty()) {
            list.add(getFavouritesHeader())
            list.addAll(data)
        }
        latestData?.let {
            list.add(getLatestHeader())
            list.addAll(it)
        }
        _apodList.value = list
    }

    private fun getLatestHeader() = AstronomyPicture(
        isTitle = true,
        title = stringResourceManager.getString(R.string.latest)
    )

    private fun getFavouritesHeader() = AstronomyPicture(
        isTitle = true,
        title = stringResourceManager.getString(R.string.favourites)
    )
}