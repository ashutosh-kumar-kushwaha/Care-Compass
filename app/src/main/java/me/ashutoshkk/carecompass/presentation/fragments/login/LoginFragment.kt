package me.ashutoshkk.carecompass.presentation.fragments.login

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import me.ashutoshkk.carecompass.common.Constants
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.databinding.FragmentLoginBinding
import me.ashutoshkk.carecompass.databinding.ProgressBarBinding
import me.ashutoshkk.carecompass.data.datastore.DataStoreManager
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.presentation.activities.MainActivity
import me.ashutoshkk.carecompass.domain.model.LogInInfo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ashutoshkk.carecompass.presentation.viewModels.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try{
                task.getResult(ApiException::class.java)
                val account = GoogleSignIn.getLastSignedInAccount(requireContext())

                if(account?.idToken != null) {
                    lifecycleScope.launch {
                        loginViewModel.googleSignIn(account.idToken!!)
                    }
                }
            }
            catch (e : Exception){
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = loginViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(
            Constants.clientId).build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)

//        binding.googleBtn.setOnClickListener {
//            signInWithGoogle()
//        }

        binding.forgotPasswordBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.RegisterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_getStartedFragment)
        }

        gsc.signOut()

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launch{
                loginViewModel.login()
            }
        }

        return binding.root
    }

    private fun signInWithGoogle() {
        val signInWithGoogleIntent = gsc.signInIntent
        startForResult.launch(signInWithGoogleIntent)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Success -> {
                    progressBar.dismiss()
                    lifecycleScope.launch {
                        val job = lifecycleScope.launch {
                            val dataStoreManager = DataStoreManager(requireContext())
                            dataStoreManager.storeLogInInfo(LogInInfo(it.data?.accessToken, it.data?.refreshToken, true, it.data?.firstname, it.data?.lastname, it.data?.roles?.get(0)?.name, it.data?.email ,it.data?.userId))
                        }
                        job.join()
                    }
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                is Resource.Error -> {
                    progressBar.dismiss()
                    Log.d("Ashu", it.message.toString())
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    progressBar.show()
                }
            }
        })

        loginViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }


}