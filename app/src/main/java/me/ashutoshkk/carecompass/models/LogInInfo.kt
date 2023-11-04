package me.ashutoshkk.carecompass.models

data class LogInInfo (
    val accessToken : String?,
    val refreshToken : String?,
    val logInState : Boolean,
    val firstName : String?,
    val lastName : String?,
    val role : String?,
    val email: String?,
    val userId: String?
)