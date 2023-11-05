package me.ashutoshkk.carecompass.presentation.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(): ViewModel() {
    val answers = mutableListOf<Int>()
}