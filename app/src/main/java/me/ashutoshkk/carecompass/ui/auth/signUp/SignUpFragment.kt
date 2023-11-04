package me.ashutoshkk.carecompass.ui.auth.signUp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.api.NetworkResult
import me.ashutoshkk.carecompass.databinding.FragmentSignUpBinding
import me.ashutoshkk.carecompass.databinding.ProgressBarBinding
import me.ashutoshkk.carecompass.datastore.DataStoreManager
import me.ashutoshkk.carecompass.models.LogInInfo
import me.ashutoshkk.carecompass.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SignUpFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding : FragmentSignUpBinding? = null
    private val binding : FragmentSignUpBinding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val signUpViewModel by viewModels<SignUpViewModel>()

    private val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        if(arguments?.getString("email")!=null && signUpViewModel.email.isEmpty()){
            signUpViewModel.email = arguments?.getString("email")!!
        }
        if(arguments?.getString("otp")!=null && signUpViewModel.otp.isEmpty()){
            signUpViewModel.otp = arguments?.getString("otp")!!
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = signUpViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.bloodGrpSpinner.onItemSelectedListener = this



        val ad: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            bloodGroups
        )

        ad.setDropDownViewResource(
            R.layout.spinner_item
        )

        binding.bloodGrpSpinner.adapter = ad


        binding.createAccountBtn.setOnClickListener{
            lifecycleScope.launch {
                var gender = ""
                if(binding.manRBtn.isChecked){
                    gender = "m"
                }
                else if(binding.womanRBtn.isChecked){
                    gender = "f"
                }
                else if(binding.otherRBtn.isChecked){
                    gender = "o"
                }
                else{
                    Toast.makeText(requireContext(), "Select a gender", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                signUpViewModel.signUp(gender)
            }
        }

        val c = Calendar.getInstance()

        signUpViewModel.year = c[Calendar.YEAR]
        signUpViewModel.month = c[Calendar.MONTH]
        signUpViewModel.day = c[Calendar.DAY_OF_MONTH]

        binding.dobBtn.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, year, monthOfYear, dayOfMonth ->

                    signUpViewModel.year = year
                    signUpViewModel.month = monthOfYear+1
                    signUpViewModel.day = dayOfMonth


                    val day2 = if(dayOfMonth < 10){
                        "0$dayOfMonth"
                    }
                    else{
                        dayOfMonth.toString()
                    }

                    val month2 = if(monthOfYear+1 < 10){
                        "0${monthOfYear+1}"
                    }
                    else{
                        (monthOfYear + 1).toString()
                    }

                    binding.dayTxtVw.text = signUpViewModel.day.toString()
                    binding.monthTxtVw.text = signUpViewModel.month.toString()
                    binding.yearTxtVw.text = signUpViewModel.year.toString()

                    signUpViewModel.dobLiveData.value = "$day2-$month2-$year"

                }, signUpViewModel.year, signUpViewModel.month-1, signUpViewModel.day)

            datePickerDialog.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.signUpResponseLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    lifecycleScope.launch {
                        val job = lifecycleScope.launch {
                            val dataStoreManager = DataStoreManager(requireContext())
                            dataStoreManager.storeLogInInfo(LogInInfo(it.data?.accessToken, it.data?.refreshToken, true, it.data?.firstname, it.data?.lastname, it.data?.roles?.get(0)?.name, it.data?.email, it.data?.email))
                        }
                        job.join()
                    }
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        })

        signUpViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        signUpViewModel.bloodGrp = bloodGroups[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}