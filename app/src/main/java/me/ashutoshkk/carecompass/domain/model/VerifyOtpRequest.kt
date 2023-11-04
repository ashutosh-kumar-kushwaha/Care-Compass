package me.ashutoshkk.carecompass.domain.model

data class VerifyOtpRequest(
    val email: String,
    val one_time_password: String
)