package me.ashutoshkk.carecompass.presentation.fragments.prediction

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import me.ashutoshkk.carecompass.databinding.FragmentPredictionBinding
import me.ashutoshkk.carecompass.databinding.ProgressBarBinding
import dagger.hilt.android.AndroidEntryPoint
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.presentation.adapters.TextRecyclerView
import me.ashutoshkk.carecompass.presentation.viewModels.PredictionViewModel

@AndroidEntryPoint
class PredictionFragment : Fragment() {

    private var _binding : FragmentPredictionBinding? = null
    private val binding : FragmentPredictionBinding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val predictionViewModel by viewModels<PredictionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPredictionBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = predictionViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        if(arguments?.getStringArrayList("list")!=null){
            predictionViewModel.symptomsList = arguments?.getStringArrayList("list")!!
//            binding.symptomsRecyclerView.adapter = TextRecyclerView(predictionViewModel.symptomsList)
//            binding.symptomsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        predictionViewModel.predict()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        predictionViewModel.predictionResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    progressBar.show()
                }
                is Resource.Success -> {
                    progressBar.dismiss()
                    binding.diseaseTxtVw.text = it.data?.Disease
                    binding.descriptionTxtVw.text = it.data?.Description
                    binding.precautionRecyclerView.adapter = TextRecyclerView(it.data?.Precautions!!)
                    binding.precautionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
                is Resource.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}