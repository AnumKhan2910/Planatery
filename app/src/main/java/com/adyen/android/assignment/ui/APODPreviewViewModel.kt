package com.adyen.android.assignment.ui

import androidx.lifecycle.*
import com.adyen.android.assignment.data.local.AstronomyPictureDao
import com.adyen.android.assignment.data.network.AstronomyPicture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class APODPreviewViewModel @Inject constructor(
    private val astronomyPictureDao: AstronomyPictureDao
): ViewModel() {

    private val _astronomyPicture = MutableLiveData<AstronomyPicture>()
    val astronomyPicture: LiveData<AstronomyPicture>
        get() = _astronomyPicture


    fun setData(data: AstronomyPicture) {
        _astronomyPicture.value = data
    }

    fun savePicture() {
        viewModelScope.launch {
            astronomyPicture.value?.let {
                val updatedData = it.copy(favourite = true)
                _astronomyPicture.value = updatedData
                astronomyPictureDao.insert(updatedData)
            }
        }
    }

    fun removePicture() {
        viewModelScope.launch {
            astronomyPicture.value?.let {
                _astronomyPicture.value = it.copy(favourite = false)
                astronomyPictureDao.deleteData(it)
            }
        }
    }
}