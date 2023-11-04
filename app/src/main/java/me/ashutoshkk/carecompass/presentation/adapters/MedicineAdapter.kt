package me.ashutoshkk.carecompass.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ashutoshkk.carecompass.databinding.MedicineItemBinding
import me.ashutoshkk.carecompass.domain.model.Medicine

class MedicineAdapter: ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(
    object : ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class MedicineViewHolder(private val binding: MedicineItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(medicine: Medicine) {
            val text = "${medicine.name} - ${medicine.frequency}"
            binding.checkbox.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        return MedicineViewHolder(MedicineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}