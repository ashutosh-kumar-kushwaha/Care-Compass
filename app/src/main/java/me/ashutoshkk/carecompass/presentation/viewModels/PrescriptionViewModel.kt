package me.ashutoshkk.carecompass.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ashutoshkk.carecompass.domain.model.Medicine

class PrescriptionViewModel : ViewModel() {
    val medicineName = MutableLiveData("")

    val medicines = mutableListOf<Medicine>()
}