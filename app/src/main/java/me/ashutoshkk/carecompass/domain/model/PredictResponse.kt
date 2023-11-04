package me.ashutoshkk.carecompass.domain.model

data class PredictResponse(
    val Description: String,
    val Disease: String,
    val Precautions: List<String>
)