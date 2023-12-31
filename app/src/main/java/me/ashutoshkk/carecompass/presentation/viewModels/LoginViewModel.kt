package me.ashutoshkk.carecompass.presentation.viewModels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.LoginResponse
import me.ashutoshkk.carecompass.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository : LoginRepository) : ViewModel() {

     val loginResponseLiveData : SingleLiveEvent<Resource<LoginResponse>> get() = loginRepository.loginResponseLiveData

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