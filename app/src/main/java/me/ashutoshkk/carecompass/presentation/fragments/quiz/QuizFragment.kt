package me.ashutoshkk.carecompass.presentation.fragments.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ashutoshkk.carecompass.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding : FragmentQuizBinding? = null
    private val binding: FragmentQuizBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}