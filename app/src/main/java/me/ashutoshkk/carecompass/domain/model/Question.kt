package me.ashutoshkk.carecompass.domain.model

data class Question(
    val id: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String
)
