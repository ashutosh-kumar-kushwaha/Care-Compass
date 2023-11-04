package me.ashutoshkk.carecompass.domain.model

data class LoginRequest(
    val email: String,
    val password: String
)