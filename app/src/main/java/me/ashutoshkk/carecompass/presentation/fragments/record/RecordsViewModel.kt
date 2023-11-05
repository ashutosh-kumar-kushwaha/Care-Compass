package me.ashutoshkk.carecompass.presentation.fragments.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.ashutoshkk.carecompass.data.repository.RecordsRepository
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(private val recordsRepository: RecordsRepository): ViewModel() {
    val records get() = recordsRepository.records

    fun getRecords(){
        viewModelScope.launch {
            recordsRepository.getRecords()
        }
    }
}