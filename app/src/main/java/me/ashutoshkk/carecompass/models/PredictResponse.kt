package me.ashutoshkk.carecompass.models

data class PredictResponse(
    val Description: String,
    val Disease: String,
    val Precautions: List<String>
)