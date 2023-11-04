package me.ashutoshkk.carecompass.presentation.fragments.forgotPasswordOtpVerification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.presentation.viewModels.ForgotPasswordOtpVerificationViewModel

class ForgotPasswordOtpVerificationFragment : Fragment() {

    private lateinit var viewModel: ForgotPasswordOtpVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_forgot_password_otp_verification,
            container,
            false
        )
    }

}