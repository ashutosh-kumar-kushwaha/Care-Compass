package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.RetrofitAPI
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.LoginRequest
import me.ashutoshkk.carecompass.domain.model.LoginResponse
import me.ashutoshkk.carecompass.domain.model.SignUpRequest
import javax.inject.Inject

class SignUpRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val signUpResponseLiveData = SingleLiveEvent<Resource<LoginResponse>>()

    suspend fun signUp(dob: String, bloodGroup: String, email : String, otp : String, firstName : String, lastName : String, gender : String, password : String){
        signUpResponseLiveData.value = Resource.Loading()
        try {
            val response = retrofitAPI.signUp(SignUpRequest(dob, bloodGroup, email, firstName, gender, lastName, otp, password))
//            Log.d("Ashu", .toString())
            when(response.code()){
                200 -> {
                    login(email, password)
                }
                401 -> signUpResponseLiveData.value = Resource.Error(401, "Invalid OTP")
                408 -> signUpResponseLiveData.value = Resource.Error(408, "Session Time-out")
                503 -> signUpResponseLiveData.value = Resource.Error(503, "Invalid Action")
                else -> signUpResponseLiveData.value = Resource.Error(response.code(),"Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            signUpResponseLiveData.value = Resource.Error(-1, e.message)
            e.printStackTrace()
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            val response = retrofitAPI.login(LoginRequest(email, password))
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        signUpResponseLiveData.postValue(Resource.Success(200, response.body()!!))
                    }
                    else{
                        signUpResponseLiveData.postValue(Resource.Error(response.code(),"Something went wrong\nError : Response is null"))
                    }
                }
                404 -> signUpResponseLiveData.postValue(Resource.Error(404, "User does not exist"))

                400 -> signUpResponseLiveData.postValue(Resource.Error(400, "Invalid Format of email or password"))

                401 -> signUpResponseLiveData.postValue(Resource.Error(401, "Wrong Password"))

                else -> signUpResponseLiveData.postValue(Resource.Error(response.code(),"Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            signUpResponseLiveData.value = Resource.Error(-1, e.message)
            e.printStackTrace()
        }
    }

}