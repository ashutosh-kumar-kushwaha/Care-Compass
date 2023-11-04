package me.ashutoshkk.carecompass.domain.model

data class LoginResponse(
    val accessToken: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val refreshToken: String,
    val roles: List<RoleX>,
    val userId: String
)