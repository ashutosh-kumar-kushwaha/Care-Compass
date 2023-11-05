package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.RetrofitAPI
import me.ashutoshkk.carecompass.domain.model.Profile
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val profileResponse = SingleLiveEvent<Resource<Profile>>()
//    val updateProfileResponse = SingleLiveEvent<Resource<Profile>>()
//    val addressResponse = SingleLiveEvent<Resource<List<Address>>>()
//    val updateEmailResponse = SingleLiveEvent<Resource<DefaultResponse>>()
//    val resetEmailResponse = SingleLiveEvent<Resource<LoginResponse>>()
//    val resendOtpResponse = SingleLiveEvent<Resource<DefaultResponse>>()
//    val deleteAddressResponse = SingleLiveEvent<Resource<DefaultResponse>>()
//    val changeProfilePicResponse = SingleLiveEvent<Resource<ChangeProfilePicResponse>>()

    suspend fun getProfile(){
        profileResponse.value = Resource.Loading()
        try {
            val response = retrofitAPI.getProfile()
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        profileResponse.value = Resource.Success(200, response.body()!!)
                    }
                    else{
                        profileResponse.value = Resource.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    profileResponse.value = Resource.Error(401, "Session expired")
                }
                else -> {
                    profileResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            profileResponse.value = Resource.Error(-1, e.message)
        }
    }

//    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest){
//        updateProfileResponse.value = Resource.Loading()
//        try {
//            val response = retrofitAPI.updateProfile(updateProfileRequest)
//            when(response.code()){
//                200 -> {
//                    if(response.body() != null){
//                        updateProfileResponse.value = Resource.Success(200, response.body()!!)
//                    }
//                    else{
//                        updateProfileResponse.value = Resource.Error(200, "Something went wrong\nError: Response is null")
//                    }
//                }
//                401 -> {
//                    updateProfileResponse.value = Resource.Error(401, "Session expired")
//                }
//                else -> {
//                    updateProfileResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e: Exception){
//            updateProfileResponse.value = Resource.Error(-1, e.message)
//        }
//    }
//
//    suspend fun getAddresses(){
//        addressResponse.value = Resource.Loading()
//        try{
//            val response = retrofitAPI.getAddresses()
//            when(response.code()){
//                200 -> {
//                    if(response.body()!=null){
//                        addressResponse.value = Resource.Success(200, response.body()!!)
//                    }
//                    else{
//                        addressResponse.value = Resource.Error(200, "Something went wrong!\nError: Response is null")
//                    }
//                }
//                else -> {
//                    addressResponse.value = Resource.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e: Exception){
//            addressResponse.value = Resource.Error(-1, e.message)
//        }
//    }
//
//    suspend fun updateEmail(email: Email){
//        updateEmailResponse.value = Resource.Loading()
//        try {
//            val response = retrofitAPI.updateEmail(email)
//            when(response.code()){
//                200 -> {
//                    updateEmailResponse.value = Resource.Success(200, DefaultResponse("OTP sent successfully", true))
//                }
//                401 -> {
//                    updateEmailResponse.value = Resource.Error(401, "Session expired")
//                }
//                406 -> {
//                    updateEmailResponse.value = Resource.Error(406, "Email already exist")
//                }
//                else -> {
//                    updateEmailResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e: Exception){
//            updateEmailResponse.value = Resource.Error(-1, e.message)
//        }
//    }
//
//    suspend fun resetEmail(resetEmailRequest: ResetEmailRequest){
//        resetEmailResponse.value = Resource.Loading()
//        try {
//            val response = retrofitAPI.resetEmail(resetEmailRequest)
//            when(response.code()){
//                200 -> {
//                    if(response.body() != null){
//                        resetEmailResponse.value = Resource.Success(200, response.body()!!)
//                    }
//                    else{
//                        resetEmailResponse.value = Resource.Error(200, "Something went wrong\nError: Response is null")
//                    }                }
//                401 -> {
//                    resetEmailResponse.value = Resource.Error(401, "Session expired")
//                }
//                406 -> {
//                    resetEmailResponse.value = Resource.Error(406, "Invalid OTP")
//                }
//                else -> {
//                    resetEmailResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e: Exception){
//            resetEmailResponse.value = Resource.Error(-1, e.message)
//        }
//    }
//
//    suspend fun resendOtp(email : Email){
//        resendOtpResponse.value = Resource.Loading()
//        try{
//            val response = retrofitAPI.updateEmail(email)
//            when(response.code()){
//                200 -> {
//                    updateEmailResponse.value = Resource.Success(200, DefaultResponse("OTP sent successfully", true))
//                }
//                401 -> {
//                    updateEmailResponse.value = Resource.Error(401, "Session expired")
//                }
//                406 -> {
//                    updateEmailResponse.value = Resource.Error(406, "Email already exist")
//                }
//                else -> {
//                    updateEmailResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e : Exception){
//            resendOtpResponse.value = Resource.Error(-1, e.message)
//        }
//    }
//
//    suspend fun deleteAddress(addressId: Int){
//        deleteAddressResponse.value = Resource.Loading()
//        try {
//            val response = retrofitAPI.deleteAddress(addressId)
//            when(response.code()){
//                200 -> {
//                    deleteAddressResponse.value = Resource.Success(200, DefaultResponse("Deleted", true))
//                }
//                401 -> {
//                    deleteAddressResponse.value = Resource.Error(401, "Session expired")
//                }
//                403 -> {
//                    deleteAddressResponse.value = Resource.Error(403, "User not allowed to perform this action")
//                }
//                else -> {
//                    deleteAddressResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e: Exception){
//            deleteAddressResponse.value = Resource.Error(-1, e.message)
//        }
//    }
//
//    suspend fun changeProfilePic(image: MultipartBody.Part){
//        changeProfilePicResponse.value = Resource.Loading()
//        try {
//            val response = retrofitAPI.changeProfilePic(image)
//            when(response.code()){
//                200 -> {
//                    if(response.body()!=null){
//                        changeProfilePicResponse.value = Resource.Success(200, response.body()!!)
//                    }
//                    else{
//                        changeProfilePicResponse.value = Resource.Error(200, "Something went wrong\nError: Response is null")
//                    }
//                }
//                401 -> {
//                    changeProfilePicResponse.value = Resource.Error(401, "Session expired")
//                }
//                else -> {
//                    changeProfilePicResponse.value = Resource.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
//                }
//            }
//        }
//        catch (e: Exception){
//            changeProfilePicResponse.value = Resource.Error(-1, e.message)
//        }
//    }
}