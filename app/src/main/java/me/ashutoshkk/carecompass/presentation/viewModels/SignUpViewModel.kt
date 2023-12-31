package me.ashutoshkk.carecompass.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpRepository : SignUpRepository) : ViewModel() {

    var email = ""
    var otp = ""
    var gender: String? = null
    val firstNameLiveData = MutableLiveData<String>("")
    val lastNameLiveData = MutableLiveData<String>("")
    val password1LiveData = MutableLiveData<String>("")
    val password2LiveData = MutableLiveData<String>("")
    val dobLiveData = MutableLiveData("")

    var bloodGrp = ""

    var day = 0
    var month = 0
    var year = 0

    val errorMessage = SingleLiveEvent<String>()

    val signUpResponseLiveData get() = signUpRepository.signUpResponseLiveData

    suspend fun signUp(gender : String) {
        if (!firstNameLiveData.value.isNullOrEmpty()) {
            if (!lastNameLiveData.value.isNullOrEmpty()) {
                if (password1LiveData.value != null && password2LiveData.value != null) {
                    if (password1LiveData.value == password2LiveData.value) {
                        if (password1LiveData.value!!.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))) {
                            signUpRepository.signUp(
                                dobLiveData.value!!,
                                bloodGrp,
                                email,
                                otp,
                                firstNameLiveData.value!!,
                                lastNameLiveData.value!!,
                                gender,
                                password1LiveData.value!!
                            )
                        } else {
                            errorMessage.value =
                                "Password must contain at least one uppercase letter, one lowercase letter, one numeric character, one special character and no spaces and must have at least 8 characters"
                        }
                    } else {
                        errorMessage.value = "Enter same passwords in both fields"
                        Log.d("Ashu", password1LiveData.value!!)
                        Log.d("Ashu", password2LiveData.value!!)
                    }
                } else {
                    errorMessage.value = "Enter passwords in both fields"
                }
            } else {
                errorMessage.value = "Enter a last name"
            }
        } else {
            errorMessage.value = "Enter a first name"
        }
    }
}