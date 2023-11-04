package me.ashutoshkk.carecompass.models

data class Symptom(
    val id: Int,
    val name: String,
    val nameWithUnderScore: String,
    var isPicked: Boolean
)