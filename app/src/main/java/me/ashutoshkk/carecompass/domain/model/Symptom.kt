package me.ashutoshkk.carecompass.domain.model

data class Symptom(
    val id: Int,
    val name: String,
    val nameWithUnderScore: String,
    var isPicked: Boolean
)