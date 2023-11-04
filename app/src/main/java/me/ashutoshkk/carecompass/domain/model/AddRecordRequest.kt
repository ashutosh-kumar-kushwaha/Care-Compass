package me.ashutoshkk.carecompass.domain.model

data class AddRecordRequest(
    val description: String,
    val from: String,
    val location: String,
    val name: String,
    val symptoms: String,
    val to: String
)