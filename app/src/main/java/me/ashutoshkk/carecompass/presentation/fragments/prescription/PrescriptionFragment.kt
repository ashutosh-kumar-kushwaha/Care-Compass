package me.ashutoshkk.carecompass.presentation.fragments.prescription

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.databinding.FragmentPredictionBinding
import me.ashutoshkk.carecompass.presentation.viewModels.PrescriptionViewModel

class PrescriptionFragment : Fragment() {

    private lateinit var viewModel: PrescriptionViewModel
    private var _binding: FragmentPredictionBinding? = null
    private val binding: FragmentPredictionBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}