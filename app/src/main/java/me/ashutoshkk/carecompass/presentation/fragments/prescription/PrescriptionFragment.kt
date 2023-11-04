package me.ashutoshkk.carecompass.presentation.fragments.prescription

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.databinding.FragmentPrescriptionBinding
import me.ashutoshkk.carecompass.domain.model.Medicine
import me.ashutoshkk.carecompass.presentation.adapters.MedicineAdapter
import me.ashutoshkk.carecompass.presentation.viewModels.PrescriptionViewModel

class PrescriptionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: PrescriptionViewModel by viewModels()
    private var _binding: FragmentPrescriptionBinding? = null
    private val binding: FragmentPrescriptionBinding get() = _binding!!

    private val doses = listOf("1", "2", "3", "4")

    private var selectedDose = "1"

    private val medicineAdapter by lazy {
        MedicineAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrescriptionBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.medicinesRecyclerView.adapter = medicineAdapter

        val ad: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            doses
        )

        binding.dosesSpinner.onItemSelectedListener = this

        ad.setDropDownViewResource(
            R.layout.spinner_item
        )

        binding.dosesSpinner.adapter = ad

        binding.submitBtn.setOnClickListener {
            submitMedicine()
        }

        binding.addBtn.setOnClickListener {
            setAlarms()
        }

        return binding.root
    }

    private fun setAlarms() {

    }

    private fun submitMedicine(){
        viewModel.medicines.add(Medicine(viewModel.medicineName.value!!, selectedDose.toInt()))
        medicineAdapter.submitList(viewModel.medicines.toList())
        viewModel.medicineName.value = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedDose = doses[p2]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}