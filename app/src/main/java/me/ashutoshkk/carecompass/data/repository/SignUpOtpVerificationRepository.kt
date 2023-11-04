package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.RetrofitAPI
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.DefaultResponse
import me.ashutoshkk.carecompass.domain.model.Email
import me.ashutoshkk.carecompass.domain.model.VerifyOtpRequest
import javax.inject.Inject

class SignUpOtpVerificationRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val responseLiveData =
        SingleLiveEvent<Resource<DefaultResponse>>()

    val resendOtpResponseLiveData = SingleLiveEvent<Resource<DefaultResponse>>()

    suspend fun verifySignUpOtp(email : String, otp : String){
        responseLiveData.value = Resource.Loading()
        try{
            val response = retrofitAPI.verifySignUpOtp(VerifyOtpRequest(email, otp))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        responseLiveData.value = Resource.Success(200, response.body()!!)
                    }
                    else{
                        responseLiveData.value = Resource.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                406 -> responseLiveData.value = Resource.Error(406, "Wrong OTP")
                403 -> responseLiveData.value = Resource.Error(403, "Session Time-out\nPlease Try Again")
                else -> responseLiveData.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            responseLiveData.value = Resource.Error(-1, e.message)
        }
    }

    suspend fun resendOtp(email : String){
        resendOtpResponseLiveData.value = Resource.Loading()
        try{
            val response = retrofitAPI.signUpEmail(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        resendOtpResponseLiveData.value = Resource.Success(200, response.body()!!)
                    }
                    else{
                        resendOtpResponseLiveData.value = Resource.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                400 -> resendOtpResponseLiveData.value = Resource.Error(400, "Email a valid email")
                409 -> resendOtpResponseLiveData.value = Resource.Error(409, "User Already Exist")
                503 -> resendOtpResponseLiveData.value = Resource.Error(503, "Unable to make your request")
                else -> resendOtpResponseLiveData.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            resendOtpResponseLiveData.value = Resource.Error(-1, e.message)
        }
    }
}