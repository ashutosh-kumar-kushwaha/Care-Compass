package me.ashutoshkk.carecompass.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrescriptionViewModel : ViewModel() {
    val medicineName = MutableLiveData<String>("")
}