package me.ashutoshkk.carecompass.ui.auth.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ashutoshkk.carecompass.SingleLiveEvent
import me.ashutoshkk.carecompass.api.NetworkResult
import me.ashutoshkk.carecompass.models.LoginResponse
import me.ashutoshkk.carecompass.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository : LoginRepository) : ViewModel() {

     val loginResponseLiveData : SingleLiveEvent<NetworkResult<LoginResponse>> get() = loginRepository.loginResponseLiveData

     val errorMessage = SingleLiveEvent<String>()

    val emailLiveData = MutableLiveData("")

    val passwordLiveData = MutableLiveData("")

    suspend fun login(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            if(passwordLiveData.value!!.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))){
                loginRepository.login(emailLiveData.value!!, passwordLiveData.value!!)
            }
            else{
                errorMessage.value = "Password must contain at least one uppercase letter, one lowercase letter, one numeric character, one special character and no spaces"
            }
        }
        else{
            errorMessage.value = "Invalid Email"
        }
    }

    suspend fun googleSignIn(token : String){
        loginRepository.signGoogle(token)
    }

}