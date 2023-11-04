package me.ashutoshkk.carecompass.models

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    val DOB: String,
    val bloodGroup: String,
    val email: String,
    val firstname: String,
    val gender: String,
    val lastname: String,
    @SerializedName("one_time_password") val otp: String,
    val password: String
)