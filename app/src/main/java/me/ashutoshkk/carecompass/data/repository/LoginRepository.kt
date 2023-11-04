package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.RetrofitAPI
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.LoginRequest
import me.ashutoshkk.carecompass.domain.model.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val loginResponseLiveData =
        SingleLiveEvent<Resource<LoginResponse>>()

    suspend fun login(email: String, password: String) {
        loginResponseLiveData.postValue(Resource.Loading())
        try {
            val response = retrofitAPI.login(LoginRequest(email, password))
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        loginResponseLiveData.postValue(Resource.Success(200, response.body()!!))
                    }
                    else{
                        loginResponseLiveData.postValue(Resource.Error(200, "Something went wrong\nError: Response is null"))
                    }
                }
                404 -> loginResponseLiveData.postValue(Resource.Error(404, "User does not exist"))

                400 -> loginResponseLiveData.postValue(Resource.Error(400, "Invalid Format of email or password"))

                401 -> loginResponseLiveData.postValue(Resource.Error(401, "Wrong Password"))

                else -> loginResponseLiveData.postValue(Resource.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            loginResponseLiveData.value = Resource.Error(-1, e.message)
        }
    }

    suspend fun signGoogle(token : String){
//        Log.d("Ashu", token)
        loginResponseLiveData.value = Resource.Loading()
        try{
            val response = retrofitAPI.signGoogle(token)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        loginResponseLiveData.value = Resource.Success(200, response.body()!!)
                    }
                    else{
                        loginResponseLiveData.value = Resource.Error(200, "Something went wrong\nError : Response is null")
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