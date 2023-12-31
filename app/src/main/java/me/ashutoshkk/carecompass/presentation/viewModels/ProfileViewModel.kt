package me.ashutoshkk.carecompass.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.ashutoshkk.carecompass.data.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
//    val addressResponse get() = profileRepository.addressResponse
    val profileResponse get() = profileRepository.profileResponse
//    val updateProfileResponse get() = profileRepository.updateProfileResponse
//    val updateEmailResponse get() = profileRepository.updateEmailResponse
//    val resetEmailResponse get() = profileRepository.resetEmailResponse
//    val resendOtpResponse get() = profileRepository.resendOtpResponse
//    val deleteAddressResponse get() = profileRepository.deleteAddressResponse
//    val changeProfilePicResponse get() = profileRepository.changeProfilePicResponse
//
//    var originalEmail = ""
//
//    val email = MutableLiveData("")
//
//    val timeLiveData = MutableLiveData<String>()
//
//    val canResend = MutableLiveData<Boolean>()
//
//    val timer = object : CountDownTimer(60000, 1000){
//        override fun onTick(millisUntilFinished: Long) {
//            val min = millisUntilFinished/60000
//            val sec = (millisUntilFinished/1000)%60
//            timeLiveData.value = if(sec < 10){
//                "0${min}:0${sec}"
//            }
//            else{
//                "0${min}:${sec}"
//            }
//        }
//
//        override fun onFinish() {
//            canResend.value = true
//        }
//    }

    fun getProfile(){
        viewModelScope.launch {
            profileRepository.getProfile()
        }
    }

////    fun updateProfile(updateProfileRequest: UpdateProfileRequest){
////        viewModelScope.launch {
////            profileRepository.updateProfile(updateProfileRequest)
////        }
////    }
////
////    fun getAddresses(){
////        viewModelScope.launch {
////            profileRepository.getAddresses()
////        }
////    }
////
////    fun resetEmail(resetEmailRequest: ResetEmailRequest){
////        viewModelScope.launch {
////            profileRepository.resetEmail(resetEmailRequest)
////        }
////    }
////
////    fun updateEmail(){
////        viewModelScope.launch {
////            profileRepository.updateEmail(Email(email.value!!))
////        }
////    }
////
////    fun resendOtp(){
////        if(canResend.value == true){
////            viewModelScope.launch {
////                profileRepository.resendOtp(Email(email.value!!))
////            }
////        }
////    }
////
////    fun deleteAddress(addressId: Int){
////        viewModelScope.launch {
////            profileRepository.deleteAddress(addressId)
////        }
////    }
////
////    fun changeProfilePic(image: MultipartBody.Part){
////        viewModelScope.launch {
////            profileRepository.changeProfilePic(image)
////        }
//    }
}