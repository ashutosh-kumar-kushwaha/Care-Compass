package me.ashutoshkk.carecompass.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.ashutoshkk.carecompass.data.repository.AddRecordRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddRecordViewModel @Inject constructor(private val addRecordRepository: AddRecordRepository): ViewModel() {

    val response get() = addRecordRepository.addRecordResponse

    fun addRecord(images: MutableList<MultipartBody.Part>, requestBody: RequestBody) {
        viewModelScope.launch {
            addRecordRepository.addRecord(images, requestBody)
        }
    }

    val clinicName = MutableLiveData("")
    val address = MutableLiveData("")
    val from = MutableLiveData("")
    val to = MutableLiveData("")
    val symptoms = MutableLiveData("")
    val description = MutableLiveData("")



}