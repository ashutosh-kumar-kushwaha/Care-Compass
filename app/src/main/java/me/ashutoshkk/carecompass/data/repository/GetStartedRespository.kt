package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.RetrofitAPI
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.Email
import me.ashutoshkk.carecompass.domain.model.DefaultResponse
import me.ashutoshkk.carecompass.domain.model.LoginResponse
import javax.inject.Inject

class GetStartedRepository @Inject constructor(private val retrofitAPI : RetrofitAPI) {

    val signUpEmailResponseLiveData =
        SingleLiveEvent<Resource<DefaultResponse>>()

    val loginResponseLiveData =
        SingleLiveEvent<Resource<LoginResponse>>()

    suspend fun signUpEmail(email : String){
        signUpEmailResponseLiveData.value = Resource.Loading()
        try{
            val response = retrofitAPI.signUpEmail(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        signUpEmailResponseLiveData.value = Resource.Success(200, response.body()!!)
                    }
                    else{
                        signUpEmailResponseLiveData.value = Resource.Error(200, "Something went wrong!\nError: Response is null")
                    }
                }
                400 -> signUpEmailResponseLiveData.value = Resource.Error(400, "Email a valid email")
                409 -> signUpEmailResponseLiveData.value = Resource.Error(409, "User Already Exist")
                503 -> signUpEmailResponseLiveData.value = Resource.Error(503, "Unable to make your request")
                else -> signUpEmailResponseLiveData.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            signUpEmailResponseLiveData.value = Resource.Error(-1, e.message)
        }
    }

    suspend fun signGoogle(token : String){
        loginResponseLiveData.value = Resource.Loading()
        try{
            val response = retrofitAPI.signGoogle(token)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        loginResponseLiveData.value = Resource.Success(200, response.body()!!)
                    }
                    else{
                        loginResponseLiveData.value = Resource.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                400 -> loginResponseLiveData.value = Resource.Error(400, "Invalid token")
                403 -> loginResponseLiveData.value = Resource.Error(403, "Either the token is expired or the token is not authorized")
                else -> loginResponseLiveData.value = Resource.Error(response.code(), "Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e: Exception){
            loginResponseLiveData.value = Resource.Error(-1, e.message)
        }
    }

}