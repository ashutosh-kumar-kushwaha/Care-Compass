package me.ashutoshkk.carecompass.presentation.fragments.consultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.databinding.FragmentConsulationBinding


class ConsultationFragment : Fragment() {

    private var _binding : FragmentConsulationBinding? = null
    private val binding: FragmentConsulationBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentConsulationBinding.inflate(inflater, container, false)

        binding.selfDiagnosisBtn.setOnClickListener {
            findNavController().navigate(R.id.action_consultationFragment_to_selfDiagnosisFragment)
        }

        binding.mentalHealthPredictionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_selfDiagnosisFragment_to_quizFragment)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}