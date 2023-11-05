package me.ashutoshkk.carecompass.presentation.fragments.record

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.databinding.FragmentRecordsBinding
import me.ashutoshkk.carecompass.databinding.ProgressBarBinding
import me.ashutoshkk.carecompass.presentation.adapters.RecordsRecyclerAdapter

@AndroidEntryPoint
class RecordsFragment : Fragment() {

    private var _binding : FragmentRecordsBinding? = null
    private val binding : FragmentRecordsBinding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val recordsViewModel by viewModels<RecordsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecordsBinding.inflate(inflater, container, false)

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = recordsViewModel

        recordsViewModel.getRecords()

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_recordFragment_to_addRecordFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordsViewModel.records.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    progressBar.show()
                }
                is Resource.Success -> {
                    progressBar.dismiss()
                    binding.recordsRecyclerView.adapter = RecordsRecyclerAdapter(it.data?.content!!)
                    binding.recordsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
    }
}