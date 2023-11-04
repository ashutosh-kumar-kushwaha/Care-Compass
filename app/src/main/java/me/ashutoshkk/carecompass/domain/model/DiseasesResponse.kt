package me.ashutoshkk.carecompass.domain.model

data class DiseasesResponse(
    val Description: String,
    val Disease: String,
    val Precautions: List<String>
)