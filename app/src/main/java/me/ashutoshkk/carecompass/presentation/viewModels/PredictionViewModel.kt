package me.ashutoshkk.carecompass.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.ashutoshkk.carecompass.data.repository.PredictionRepository
import me.ashutoshkk.carecompass.domain.model.PredictRequest
import javax.inject.Inject

@HiltViewModel
class PredictionViewModel @Inject constructor(private val predictionRepository: PredictionRepository) : ViewModel() {

    var symptomsList = listOf<String>()

    val predictionResponse get() = predictionRepository.predictResponse

    fun predict(){
        viewModelScope.launch {
            predictionRepository.predict(PredictRequest(symptomsList))
        }
    }
}